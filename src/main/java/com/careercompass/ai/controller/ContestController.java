package com.careercompass.ai.controller;

import com.careercompass.ai.model.Contest;
import com.careercompass.ai.model.ContestSubmission;
import com.careercompass.ai.model.User;
import com.careercompass.ai.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contests")
@RequiredArgsConstructor
public class ContestController {

    private final ContestService contestService;

    @GetMapping("/active")
    public ResponseEntity<List<Contest>> getActiveContests() {
        return ResponseEntity.ok(contestService.getActiveContests());
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<ContestSubmission> submitToContest(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        
        Long problemId = Long.parseLong(payload.get("problemId"));
        String code = payload.get("code");
        String language = payload.get("language");
        
        ContestSubmission submission = contestService.submitToContest(user, id, problemId, code, language);
        return ResponseEntity.ok(submission);
    }

    @GetMapping("/{id}/leaderboard")
    public ResponseEntity<List<Map<String, Object>>> getLeaderboard(@PathVariable Long id) {
        return ResponseEntity.ok(contestService.getLeaderboard(id));
    }
}
