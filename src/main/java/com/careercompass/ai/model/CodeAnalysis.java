package com.careercompass.ai.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "code_analyses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission submission;

    @Column(columnDefinition = "TEXT")
    private String aiFeedback;

    @Column(columnDefinition = "TEXT")
    private String suggestedOptimization;
}
