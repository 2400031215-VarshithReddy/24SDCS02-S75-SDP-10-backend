package com.careercompass.ai.service;

import com.careercompass.ai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class AdminService {

    private final UserRepository userRepository;
    private final ResultRepository resultRepository;
    private final ProblemRepository problemRepository;
    private final TestCaseRepository testCaseRepository;
    private final CourseRepository courseRepository;
    private final CareerPathRepository careerPathRepository;

    public Map<String, Object> getAdminStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        
        Map<String, Double> averages = new HashMap<>();
        averages.put("Analytical", resultRepository.getAverageAnalyticalScore());
        averages.put("Creative", resultRepository.getAverageCreativeScore());
        averages.put("Technical", resultRepository.getAverageTechnicalScore());
        averages.put("Social", resultRepository.getAverageSocialScore());
        
        stats.put("averageScores", averages);
        return stats;
    }

    public Object getAllResults() {
        return resultRepository.findAll();
    }

    // Problem Management
    public java.util.List<com.careercompass.ai.model.Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public com.careercompass.ai.model.Problem createProblem(com.careercompass.ai.model.Problem problem) {
        return problemRepository.save(problem);
    }

    public com.careercompass.ai.model.Problem updateProblem(Long id, com.careercompass.ai.model.Problem problemDetails) {
        com.careercompass.ai.model.Problem problem = problemRepository.findById(id).orElseThrow();
        problem.setTitle(problemDetails.getTitle());
        problem.setDescription(problemDetails.getDescription());
        problem.setDifficulty(problemDetails.getDifficulty());
        problem.setTags(problemDetails.getTags());
        problem.setConstraints(problemDetails.getConstraints());
        problem.setTimeLimit(problemDetails.getTimeLimit());
        problem.setMemoryLimit(problemDetails.getMemoryLimit());
        return problemRepository.save(problem);
    }

    public void deleteProblem(Long id) {
        problemRepository.deleteById(id);
    }

    // Course Management
    public java.util.List<com.careercompass.ai.model.Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public com.careercompass.ai.model.Course createCourse(com.careercompass.ai.model.Course course) {
        return courseRepository.save(course);
    }

    public com.careercompass.ai.model.Course updateCourse(Long id, com.careercompass.ai.model.Course courseDetails) {
        com.careercompass.ai.model.Course course = courseRepository.findById(id).orElseThrow();
        course.setTitle(courseDetails.getTitle());
        course.setDescription(courseDetails.getDescription());
        course.setDifficulty(courseDetails.getDifficulty());
        course.setDurationHours(courseDetails.getDurationHours());
        course.setTopics(courseDetails.getTopics());
        course.setTrending(courseDetails.isTrending());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
