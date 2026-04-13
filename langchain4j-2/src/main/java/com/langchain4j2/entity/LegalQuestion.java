package com.langchain4j2.entity;

import dev.langchain4j.model.input.structured.StructuredPrompt;
import lombok.Data;

@StructuredPrompt("根据{{legal}}法律规定，解答以下问题：{{question}}")
@Data
public class LegalQuestion {
    private String legal;
    private String question;
}
