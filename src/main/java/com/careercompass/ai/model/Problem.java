package com.careercompass.ai.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "problems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String difficulty; // Easy, Medium, Hard

    @Column(nullable = false)
    private String tags; // Comma separated, e.g., "Arrays, DP"

    @Column(nullable = false, columnDefinition = "TEXT")
    private String constraints;

    @Builder.Default
    private int timeLimit = 1000; // in ms

    @Builder.Default
    private int memoryLimit = 256; // in MB

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
