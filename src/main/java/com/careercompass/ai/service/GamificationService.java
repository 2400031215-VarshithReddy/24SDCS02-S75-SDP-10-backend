package com.careercompass.ai.service;

import com.careercompass.ai.model.Problem;
import com.careercompass.ai.model.User;
import com.careercompass.ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GamificationService {
    
    private final UserRepository userRepository;

    public void awardXpForProblem(User user, Problem problem) {
        int xpGain = 0;
        switch (problem.getDifficulty().toLowerCase()) {
            case "easy": xpGain = 10; break;
            case "medium": xpGain = 20; break;
            case "hard": xpGain = 30; break;
            default: xpGain = 10;
        }

        addXp(user, xpGain);
    }

    public void awardXpForContest(User user) {
        addXp(user, 50); // Flat 50 XP for contest participation
    }

    private void addXp(User user, int amount) {
        user.setXp(user.getXp() + amount);
        
        // Simple leveling logic: level = (xp / 100) + 1
        int newLevel = (user.getXp() / 100) + 1;
        user.setLevel(newLevel);
        
        userRepository.save(user);
    }
}
