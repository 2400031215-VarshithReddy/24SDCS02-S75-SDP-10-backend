package com.careercompass.ai.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "results")
@Data
@NoArgsConstructor
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    private int analyticalScore;
    private int creativeScore;
    private int technicalScore;
    private int socialScore;
    private int totalScore;

    @Column(columnDefinition = "TEXT")
    private String aiRecommendation;

    private LocalDateTime completedAt;

    @PrePersist
    protected void onComplete() {
        this.completedAt = LocalDateTime.now();
    }
}
