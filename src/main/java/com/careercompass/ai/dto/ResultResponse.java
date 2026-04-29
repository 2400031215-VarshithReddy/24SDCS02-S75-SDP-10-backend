package com.careercompass.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponse {
    private Long id;
    private int analyticalScore;
    private int creativeScore;
    private int technicalScore;
    private int socialScore;
    private int totalScore;
    private String aiRecommendation;
    private LocalDateTime date;
}
