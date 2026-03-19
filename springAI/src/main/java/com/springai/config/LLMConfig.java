package com.springai.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import com.alibaba.cloud.ai.dashscope.spec.DashScopeModel;
import com.alibaba.cloud.ai.transformer.splitter.RecursiveCharacterTextSplitter;
import com.alibaba.cloud.ai.transformer.splitter.SentenceSplitter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiEmbeddingModel;
import org.springframework.ai.zhipuai.ZhiPuAiEmbeddingOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPooled;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * LLM 配置类
 */
@Configuration
public class LLMConfig {

    /**
     * deepseek model
     * @return
     */
    @Bean("deepseekModel")
    public DashScopeChatModel deepseekModel(){
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("QWEN_API_KEY")).build())
                .defaultOptions(DashScopeChatOptions.builder().model(DashScopeModel.ChatModel.DEEPSEEK_V3.value).build())
                .build();
    }

    /**
     * qwen model
     * @return
     */
    @Bean("qwenModel")
    public DashScopeChatModel qwenModel(){
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("QWEN_API_KEY")).build())
                .defaultOptions(DashScopeChatOptions.builder().model(DashScopeModel.ChatModel.QWEN3_MAX.value).build())
                .build();
    }


    /**
     * qwen chat client
     * @param dashScopeChatModel
     * @return
     */
    @Bean("qwenChatClient")
    public ChatClient qwenChatClient(@Qualifier("qwenModel") DashScopeChatModel dashScopeChatModel){
        return ChatClient.builder(dashScopeChatModel).build();
    }

    /**
     * zhipu chat client
     * @param chatModel
     * @return
     */
    @Bean("zhipuChatClient")
    public ChatClient zhipuChatClient(ZhiPuAiChatModel chatModel){
        return ChatClient.builder(chatModel).build();
    }

    /**
     * dashscope embedding model
     * @return
     */
    @Primary
    @Bean("dashScopeEmbeddingModel")
    public EmbeddingModel dashScopeEmbeddingModel(){
        return DashScopeEmbeddingModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("QWEN_API_KEY")).build())
                .defaultOptions(DashScopeEmbeddingOptions.builder().model(DashScopeModel.EmbeddingModel.EMBEDDING_V4.value).build())
                .build();
    }

    /**
     * zhiPu embedding model
     * @return
     */
    @Bean("zhiPuEmbeddingModel")
    public EmbeddingModel zhiPuEmbeddingModel(){
        ZhiPuAiApi zhiPuAiApi = ZhiPuAiApi.builder()
                .apiKey("6f0540468b104e149781575eddcb7aef.zVbuBH6XOtRpWBLJ")
                .baseUrl("https://open.bigmodel.cn/api/paas")
                .build();
        ZhiPuAiEmbeddingOptions zhiPuAiEmbeddingOptions = ZhiPuAiEmbeddingOptions.builder()
                .model("Embedding-3")
                .build();
        return new ZhiPuAiEmbeddingModel(zhiPuAiApi, MetadataMode.EMBED,zhiPuAiEmbeddingOptions);
    }


    /**
     * 将数据存如到向量数据库中
     * @return
     * @throws IOException
     */
    @Bean
    public CommandLineRunner store(RedisVectorStore redisVectorStore,
                                   @Qualifier("dashScopeEmbeddingModel") EmbeddingModel embeddingModel) throws IOException {
        return args -> {
            ClassPathResource resource = new ClassPathResource("古代诗歌常用意象.txt");
            String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            // token分割 严格按照token数量分割文本
//            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
//            List<Document> documentList = tokenTextSplitter.apply(List.of(new Document(content)));
            // 句子分割器 先用NIP模型识别句子，在按token合并句子
//            SentenceSplitter sentenceSplitter = new SentenceSplitter();
//            List<Document> documentList = sentenceSplitter.apply(List.of(new Document(content)));
            // 递归分割器
            RecursiveCharacterTextSplitter recursiveCharacterTextSplitter = new RecursiveCharacterTextSplitter();
            List<Document> documentList = recursiveCharacterTextSplitter.apply(List.of(new Document(content)));
            int batchSize = 10;
            for (int i = 0; i < documentList.size(); i += batchSize) {
                int end = Math.min(i + batchSize, documentList.size());
                List<Document> batch = documentList.subList(i, end);
                System.out.println("正在处理批次: " + (i/batchSize + 1) + ", 数量: " + batch.size());
                redisVectorStore.add(batch);
            }
//            redisVectorStore.add(documentList);
        };
    }

//    @Bean
//    public JedisPooled jedisPooled(){
//        HostAndPort hostAndPort = new HostAndPort("1.94.101.207", 6379);
//        JedisClientConfig jedisClientConfig = DefaultJedisClientConfig.builder()
//                .password("PGFmy8pbBxeJYpYZ")
//                .build();
//        return new JedisPooled(hostAndPort,jedisClientConfig);
//    }
//
//    @Bean
//    public RedisVectorStore redisVectorStore(JedisPooled jedisPooled,
//                                             @Qualifier("dashScopeEmbeddingModel") EmbeddingModel embeddingModel) {
//        // 需要获取 JedisPooled，Spring AI 提供了转换方法
//        return RedisVectorStore.builder(jedisPooled, embeddingModel)
//                .indexName("spring-ai-alibaba-rag")  // 👈 显式指定
//                .prefix("rag:")                       // 👈 显式指定前缀
//                .initializeSchema(true)               // 👈 强制初始化
//                .build();
//    }

}
