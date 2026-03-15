package com.springai.service.impl;

import com.springai.service.EmbeddingService;
import org.springframework.ai.zhipuai.ZhiPuAiEmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddingServiceImpl implements EmbeddingService {

    protected static final List<String> docs = List.of("美食非常美味，服务员也很友好","这部电影既刺激又令人兴奋","阅读书籍是扩展知识的好方法");
    private final List<float[]> docVectors;

    private ZhiPuAiEmbeddingModel embeddingModel;
    @Override
    public String queryBestMatch(String message) {
        //1. 对用户输入的文本进行向量化处理
        float[] queryVector = embeddingModel.embed(message);
        //记录目前最大的相识度
        double bestSimilarity = -1;
        //记录与当前文本最相似的文本的索引
        int bestIndex = -1;
        //2. 遍历docVectors来与用户传入的文本向量进行计算相识度，找出最相似的文本
        for (int i = 0; i < docVectors.size(); i++) {
            double similarity = cosineSimilarity(queryVector, docVectors.get(i));
            if(similarity > bestSimilarity){
                bestSimilarity = similarity;
                bestIndex = i;
            }
        }
        return docs.get(bestIndex);
    }

    public EmbeddingServiceImpl(ZhiPuAiEmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
        this.docVectors = embeddingModel.embed(docs);
    }

    /**
     * 计算两个向量的相似度
     * @param vector1
     * @param vector2
     * @return
     */
    private double cosineSimilarity(float[] vector1, float[] vector2){
        double dot = 0, na=0, nb=0;
        for (int i = 0; i < vector1.length; i++) {
            dot += vector1[i] * vector2[i];
            na += vector1[i] * vector1[i];
            nb += vector2[i] * vector2[i];
        }
        return dot / (Math.sqrt(na) * Math.sqrt(nb));
    }
}
