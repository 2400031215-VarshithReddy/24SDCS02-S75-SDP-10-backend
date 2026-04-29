package com.careercompass.ai.config;

import com.careercompass.ai.model.Contest;
import com.careercompass.ai.model.ContestProblem;
import com.careercompass.ai.model.Problem;
import com.careercompass.ai.repository.ContestProblemRepository;
import com.careercompass.ai.repository.ContestRepository;
import com.careercompass.ai.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(2) // Run after ProblemSeeder
@SuppressWarnings("null")
public class ContestSeeder implements CommandLineRunner {

    private final ContestRepository contestRepository;
    private final ProblemRepository problemRepository;
    private final ContestProblemRepository contestProblemRepository;

    @Override
    public void run(String... args) {
        if (contestRepository.count() == 0) {
            seedContests();
        }
    }

    private void seedContests() {
        Contest c1 = Contest.builder()
                .name("Global Architectural Sprint #1")
                .startTime(LocalDateTime.now().minusHours(1))
                .endTime(LocalDateTime.now().plusHours(23))
                .build();
        contestRepository.save(c1);

        List<Problem> problems = problemRepository.findAll();
        if (!problems.isEmpty()) {
            for (int i = 0; i < Math.min(3, problems.size()); i++) {
                ContestProblem cp = ContestProblem.builder()
                        .contest(c1)
                        .problem(problems.get(i))
                        .order(i + 1)
                        .build();
                contestProblemRepository.save(cp);
            }
        }

        System.out.println("✅ Contests Seeded.");
    }
}
