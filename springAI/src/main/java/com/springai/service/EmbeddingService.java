package com.springai.service;

public interface EmbeddingService {

    /**
     * 通过向量化获取最匹配的文本
     * @param message
     * @return
     */
    String queryBestMatch(String message);
}
