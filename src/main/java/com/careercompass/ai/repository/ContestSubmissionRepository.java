package com.careercompass.ai.repository;

import com.careercompass.ai.model.ContestSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestSubmissionRepository extends JpaRepository<ContestSubmission, Long> {
    List<ContestSubmission> findByContestId(Long contestId);
    List<ContestSubmission> findByUserIdAndContestId(Long userId, Long contestId);
}
