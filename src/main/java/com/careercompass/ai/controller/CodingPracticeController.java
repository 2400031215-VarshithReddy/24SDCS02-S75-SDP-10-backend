package com.careercompass.ai.controller;

import com.careercompass.ai.model.Problem;
import com.careercompass.ai.model.Submission;
import com.careercompass.ai.model.User;
import com.careercompass.ai.service.CodingPracticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class CodingPracticeController {

    private final CodingPracticeService codingPracticeService;

    @GetMapping
    public ResponseEntity<List<Problem>> getAllProblems() {
        return ResponseEntity.ok(codingPracticeService.getAllProblems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Problem> getProblemById(@PathVariable Long id) {
        return codingPracticeService.getProblemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<Submission> submitCode(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        
        String code = payload.get("code");
        String language = payload.get("language");
        
        Submission submission = codingPracticeService.submitCode(user, id, code, language);
        return ResponseEntity.ok(submission);
    }

    @PostMapping("/{id}/run")
    public ResponseEntity<Map<String, String>> runCode(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        
        // Simulating run logic - in production this would call ExecutionService
        String output = "Sample Output: Success\nMemory: 24MB\nTime: 0.05s";
        return ResponseEntity.ok(Map.of("output", output));
    }

    @GetMapping("/submission/{submissionId}/analysis")
    public ResponseEntity<?> getAnalysis(@PathVariable Long submissionId) {
        return ResponseEntity.ok(codingPracticeService.getAnalysisForSubmission(submissionId));
    }
}
