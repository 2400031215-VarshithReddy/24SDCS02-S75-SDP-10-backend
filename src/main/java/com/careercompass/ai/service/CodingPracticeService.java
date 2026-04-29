package com.careercompass.ai.service;

import com.careercompass.ai.model.*;
import com.careercompass.ai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class CodingPracticeService {
    private final ProblemRepository problemRepository;
    private final SubmissionRepository submissionRepository;
    private final CodeAnalysisRepository codeAnalysisRepository;
    private final ExecutionService executionService;
    private final GeminiService geminiService;
    private final GamificationService gamificationService;

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public Optional<Problem> getProblemById(Long id) {
        return problemRepository.findById(id);
    }

    public Submission submitCode(User user, Long problemId, String code, String language) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid problem ID"));

        // Evaluate code
        String status = executionService.evaluate(problem, code, language);

        Submission submission = Submission.builder()
                .user(user)
                .problem(problem)
                .code(code)
                .language(language)
                .status(status)
                .submittedAt(LocalDateTime.now())
                .build();
        submission = submissionRepository.save(submission);

        if ("Accepted".equalsIgnoreCase(status)) {
            gamificationService.awardXpForProblem(user, problem);
        }

        // Trigger AI Analysis asynchronously or synchronously
        String aiFeedbackPayload = geminiService.analyzeCode(code, problem, status);
        CodeAnalysis analysis = CodeAnalysis.builder()
                .submission(submission)
                .aiFeedback(aiFeedbackPayload)
                .suggestedOptimization(geminiService.suggestOptimization(code, problem))
                .build();
        codeAnalysisRepository.save(analysis);

        return submission;
    }

    public CodeAnalysis getAnalysisForSubmission(Long submissionId) {
        return codeAnalysisRepository.findBySubmissionId(submissionId);
    }
}
