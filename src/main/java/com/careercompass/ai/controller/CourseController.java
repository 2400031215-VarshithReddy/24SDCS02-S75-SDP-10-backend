package com.careercompass.ai.controller;

import com.careercompass.ai.model.Course;
import com.careercompass.ai.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class CourseController {

    private final CourseRepository courseRepo;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllCourses() {
        return ResponseEntity.ok(courseRepo.findAll().stream().map(this::toMap).collect(Collectors.toList()));
    }

    @GetMapping("/trending")
    public ResponseEntity<List<Map<String, Object>>> getTrending() {
        return ResponseEntity.ok(courseRepo.findByTrendingTrue().stream().map(this::toMap).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getCourse(@PathVariable Long id) {
        Course c = courseRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
        Map<String, Object> m = toMap(c);
        m.put("pathName", c.getCareerPath() != null ? c.getCareerPath().getName() : null);
        return ResponseEntity.ok(m);
    }

    private Map<String, Object> toMap(Course c) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", c.getId());
        m.put("title", c.getTitle());
        m.put("description", c.getDescription());
        m.put("difficulty", c.getDifficulty());
        m.put("durationHours", c.getDurationHours());
        m.put("topics", c.getTopics());
        m.put("trending", c.isTrending());
        m.put("careerPathId", c.getCareerPath() != null ? c.getCareerPath().getId() : null);
        return m;
    }
}
