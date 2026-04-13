package com.springai.controller;

import com.springai.service.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rag")
@RestController
@RequiredArgsConstructor
public class RagController {

    private final RagService ragService;

    /**
     * 用户输入的问题，通过知识库回答
     * @param message
     * @return
     */
    @GetMapping(value = "/answer")
    public String answer(@RequestParam(value = "message") String message){
        return ragService.answer(message);
    }
}
