package com.careercompass.ai.dto;

import lombok.Data;

@Data
public class AnswerSubmitRequest {
    private Long questionId;
    private int score;
}
