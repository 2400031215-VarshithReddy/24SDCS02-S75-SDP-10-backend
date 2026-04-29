package com.careercompass.ai.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "certificates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "certificate_title", nullable = false)
    private String certificateTitle;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "certificate_id", unique = true)
    private String certificateId;

    @Column(name = "verification_statement", columnDefinition = "TEXT")
    private String verificationStatement;

    @PrePersist
    protected void onIssue() {
        this.issuedAt = LocalDateTime.now();
        this.certificateId = "CC-" + System.currentTimeMillis();
    }
}
