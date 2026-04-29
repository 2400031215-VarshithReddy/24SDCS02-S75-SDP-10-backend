package com.careercompass.ai.service;

import com.careercompass.ai.model.*;
import com.careercompass.ai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ContestService {

    private final ContestRepository contestRepository;
    private final ContestProblemRepository contestProblemRepository;
    private final ContestSubmissionRepository contestSubmissionRepository;
    private final ExecutionService executionService;
    private final GamificationService gamificationService;

    public List<Contest> getActiveContests() {
        LocalDateTime now = LocalDateTime.now();
        return contestRepository.findByStartTimeBeforeAndEndTimeAfter(now, now);
    }

    public ContestSubmission submitToContest(User user, Long contestId, Long problemId, String code, String language) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid contest ID"));
        
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(contest.getStartTime()) || now.isAfter(contest.getEndTime())) {
            throw new IllegalStateException("Contest is not active");
        }

        ContestProblem cp = contestProblemRepository.findByContestIdOrderByOrderAsc(contestId)
                .stream()
                .filter(p -> p.getProblem().getId().equals(problemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Problem not in contest"));

        String status = executionService.evaluate(cp.getProblem(), code, language);

        ContestSubmission submission = ContestSubmission.builder()
                .user(user)
                .contest(contest)
                .problem(cp.getProblem())
                .status(status)
                .submittedAt(now)
                .build();
        
        submission = contestSubmissionRepository.save(submission);

        // If it's the first accepted submission for this user in this contest, we could award XP
        // For simplicity, award XP for participation if not already awarded (could be optimized)
        gamificationService.awardXpForContest(user); 

        return submission;
    }

    public List<Map<String, Object>> getLeaderboard(Long contestId) {
        List<ContestSubmission> submissions = contestSubmissionRepository.findByContestId(contestId);
        
        // Calculate leaderboard: Map user to stats
        Map<Long, UserStats> statsMap = new HashMap<>();
        
        for (ContestSubmission sub : submissions) {
            User user = sub.getUser();
            statsMap.putIfAbsent(user.getId(), new UserStats(user.getName()));
            
            UserStats stats = statsMap.get(user.getId());
            if ("Accepted".equalsIgnoreCase(sub.getStatus())) {
                stats.problemsSolved++;
                // penalty could be based on time since start. Simplified here.
            } else {
                stats.penalty += 10; // 10 penalty points for wrong answer
            }
        }
        
        List<Map<String, Object>> leaderboard = new ArrayList<>();
        statsMap.values().stream()
                .sorted((a, b) -> {
                    if (a.problemsSolved != b.problemsSolved) {
                        return Integer.compare(b.problemsSolved, a.problemsSolved);
                    }
                    return Integer.compare(a.penalty, b.penalty);
                })
                .forEach(stat -> {
                    Map<String, Object> entry = new HashMap<>();
                    entry.put("name", stat.name);
                    entry.put("problemsSolved", stat.problemsSolved);
                    entry.put("penalty", stat.penalty);
                    leaderboard.add(entry);
                });
                
        return leaderboard;
    }
    
    private static class UserStats {
        String name;
        int problemsSolved = 0;
        int penalty = 0;
        
        UserStats(String name) {
            this.name = name;
        }
    }
}
