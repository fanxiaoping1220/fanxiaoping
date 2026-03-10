package com.langchain4j.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CommonConfig {

    //    @Autowired
//    private OpenAiChatModel model;
    private final ChatMemoryStore redisChatMemoryStore;
    private final EmbeddingModel embeddingModel;

//    @Bean
//    public ConsultantService consultantService() {
//        return AiServices.builder(ConsultantService.class)
//                .chatModel(model)
//                .build();
//    }

    /**
     * 配置会话记忆
     *
     * @return
     */
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().maxMessages(20).build();
    }

    /**
     * 配置会话记忆提供者
     *
     * @return
     */
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory.builder().id(memoryId).maxMessages(20).chatMemoryStore(redisChatMemoryStore).build();
            }
        };
    }

    /**
     * 配置嵌入向量存储
     *
     * @return
     */
    @Bean
    public EmbeddingStore<TextSegment> store() {
        //1.加载文档
        List<Document> documentList = ClassPathDocumentLoader.loadDocuments("content");
        //2.创建嵌入向量存储
        InMemoryEmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();
        //3.将文档导入向量存储
        for (Document document : documentList) {
            TextSegment segment = TextSegment.from(document.text());
            Embedding embedding = embeddingModel.embed(segment).content();
            store.add(embedding, segment);
        }
        return store;
    }

    /**
     * 配置构建向量数据库检索对象
     *
     * @return
     */
    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore<TextSegment> store) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .minScore(0.5).maxResults(3).build();
    }

}
