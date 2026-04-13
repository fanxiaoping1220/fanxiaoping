package com.langchain4j.config;

import dev.langchain4j.community.store.embedding.redis.RedisEmbeddingStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
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
    private final RedisEmbeddingStore redisEmbeddingStore;

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
     * EmbeddingStore的对象，这个对象不能重复，所以这里使用store
     * InMemoryEmbeddingStore是内存向量存储，服务重启就需要重新加载数据，一旦数据多了启动会很慢，重启之后会丢失数据
     * RedisEmbeddingStore是redis向量存储，服务重启不需要重新加载数据，数据是存储在redis中的，不会丢失
     * @return
     */
//    @Bean
    public EmbeddingStore<TextSegment> store() {
        //1.加载文档
        List<Document> documentList = ClassPathDocumentLoader.loadDocuments("content");
        //pdf解析加载器
//        List<Document> documentList = ClassPathDocumentLoader.loadDocuments("content",new ApachePdfBoxDocumentParser());
        //文件加载器要求文件路径
//        List<Document> documentList = FileSystemDocumentLoader.loadDocuments("/Users/songzj/Desktop/workspace/fanxiaoping/langchain4j/src/main/resources/content");
        //2.创建嵌入内存向量存储
//        InMemoryEmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();
        //3.将文档导入向量存储
        for (Document document : documentList) {
            TextSegment segment = TextSegment.from(document.text());
            Embedding embedding = embeddingModel.embed(segment).content();
            redisEmbeddingStore.add(embedding, segment);
        }
        return redisEmbeddingStore;
    }

    /**
     * 配置构建内存向量数据库检索对象
     *
     * @return
     */
//    @Bean
//    public ContentRetriever contentRetriever(EmbeddingStore<TextSegment> store) {
//        return EmbeddingStoreContentRetriever.builder()
//                .embeddingStore(store)
//                .embeddingModel(embeddingModel)
//                .minScore(0.5).maxResults(3).build();
//    }

    /**
     * 配置构建redis向量数据库检索对象
     *
     * @return
     */
    @Bean
    public ContentRetriever contentRetriever() {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(redisEmbeddingStore)
                .embeddingModel(embeddingModel)
                .minScore(0.5).maxResults(3).build();
    }

}
