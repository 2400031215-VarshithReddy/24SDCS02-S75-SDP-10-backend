package com.careercompass.ai.config;

import com.careercompass.ai.model.Problem;
import com.careercompass.ai.model.TestCase;
import com.careercompass.ai.repository.ProblemRepository;
import com.careercompass.ai.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
@SuppressWarnings("null")
public class ProblemSeeder implements CommandLineRunner {

    private final ProblemRepository problemRepository;
    private final TestCaseRepository testCaseRepository;

    @Override
    public void run(String... args) {
        if (problemRepository.count() == 0) {
            seedProblems();
        }
    }

    private void seedProblems() {
        Problem p1 = Problem.builder()
                .title("Two Sum")
                .description("Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.")
                .difficulty("Easy")
                .tags("Array,Hash Table")
                .constraints("2 <= nums.length <= 10^4\n-10^9 <= nums[i] <= 10^9\n-10^9 <= target <= 10^9")
                .build();
        problemRepository.save(p1);

        TestCase tc1 = TestCase.builder()
                .problem(p1)
                .input("[2,7,11,15], 9")
                .expectedOutput("[0,1]")
                .hidden(false)
                .build();
        testCaseRepository.save(tc1);

        Problem p2 = Problem.builder()
                .title("Longest Substring Without Repeating Characters")
                .description("Given a string s, find the length of the longest substring without repeating characters.")
                .difficulty("Medium")
                .tags("String,Sliding Window")
                .constraints("0 <= s.length <= 5 * 10^4\ns consists of English letters, digits, symbols and spaces.")
                .build();
        problemRepository.save(p2);

        TestCase tc2 = TestCase.builder()
                .problem(p2)
                .input("\"abcabcbb\"")
                .expectedOutput("3")
                .hidden(false)
                .build();
        testCaseRepository.save(tc2);

        Problem p3 = Problem.builder()
                .title("Median of Two Sorted Arrays")
                .description("Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.")
                .difficulty("Hard")
                .tags("Array,Binary Search,Divide and Conquer")
                .constraints("nums1.length == m\nnums2.length == n\n0 <= m <= 1000\n0 <= n <= 1000\n1 <= m + n <= 2000")
                .build();
        problemRepository.save(p3);

        TestCase tc3 = TestCase.builder()
                .problem(p3)
                .input("[1,3], [2]")
                .expectedOutput("2.0")
                .hidden(false)
                .build();
        testCaseRepository.save(tc3);

        System.out.println("✅ Problems and TestCases Seeded.");
    }
}
