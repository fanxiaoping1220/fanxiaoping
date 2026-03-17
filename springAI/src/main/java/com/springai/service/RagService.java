package com.springai.service;

import reactor.core.publisher.Flux;

public interface RagService {

    String answer(String message);

    Flux<String> streamAnswer(String message);
}
