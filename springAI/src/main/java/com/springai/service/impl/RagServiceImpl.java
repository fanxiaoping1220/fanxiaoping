package com.springai.service.impl;

import com.springai.service.RagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.zhipuai.ZhiPuAiEmbeddingModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RagServiceImpl implements RagService {

    private final ZhiPuAiEmbeddingModel embeddingModel;
    private final ChatClient chatClient;

    /**
     * 存储切割后的文档
     */
    private final List<String> docs = new ArrayList<>();

    /**
     * 存储文档向量
     */
    private final List<float[]> vectors = new ArrayList<>();

    public RagServiceImpl(ZhiPuAiEmbeddingModel embeddingModel,ChatClient.Builder chatClientBuilder) throws IOException {
        this.chatClient = chatClientBuilder.build();
        this.embeddingModel = embeddingModel;
        ClassPathResource resource = new ClassPathResource("古代诗歌常用意象.txt");
        String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String[] split = content.split("----");
        for (String part : split) {
            log.info("part: {}", part);
            //存储切分后的文本
            docs.add(part);
            //存储切分后的文本的向量化
            vectors.add(embeddingModel.embed(part));
        }
    }


    @Override
    public String answer(String message) {
        log.info("message: {}", message);
        float[] queryVector = embeddingModel.embed(message);
        //top2 最相似的两个文档
        double best1 = -1;
        int index1 = -1;
        double best2 = -1;
        int index2 = -1;
        for (int i = 0; i < vectors.size(); i++) {
            double similarity = cosineSimilarity(queryVector, vectors.get(i));
            if (similarity > best1) {
                best2 = best1;
                index2 = index1;
                best1 = similarity;
                index1 = i;
            } else if (similarity > best2) {
                best2 = similarity;
                index2 = i;
            }
        }
        String content = "";
        if(index1 != -1){
            content = docs.get(index1) + (index2 > 0 ? "\n----\n " + docs.get(index2) : "");
        }
        String prompt = "以下是知识库内容：\n" + content + "\n 请根据知识库内容回答用户问题：" + message;
        //将获取到的top2文档，作为提示词交给chat大模型回复
        return chatClient.prompt().system("你是知识组手，结合上下文回答用户问题").user(prompt).call().content();
    }

    /**
     * 计算两个向量的相似度
     * @param vector1
     * @param vector2
     * @return
     */
    private double cosineSimilarity(float[] vector1, float[] vector2) {
        double dot = 0, na = 0, nb = 0;
        for (int i = 0; i < vector1.length; i++) {
            dot += vector1[i] * vector2[i];
            na += vector1[i] * vector1[i];
            nb += vector2[i] * vector2[i];
        }
        return dot / (Math.sqrt(na) * Math.sqrt(nb));
    }
}
