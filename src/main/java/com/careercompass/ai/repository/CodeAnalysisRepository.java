package com.careercompass.ai.repository;

import com.careercompass.ai.model.CodeAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeAnalysisRepository extends JpaRepository<CodeAnalysis, Long> {
    CodeAnalysis findBySubmissionId(Long submissionId);
}
