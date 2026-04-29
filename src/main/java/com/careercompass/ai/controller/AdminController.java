package com.careercompass.ai.controller;

import com.careercompass.ai.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats() {
        return ResponseEntity.ok(adminService.getAdminStats());
    }

    @GetMapping("/results")
    public ResponseEntity<Object> getAllResults() {
        return ResponseEntity.ok(adminService.getAllResults());
    }

    // Problems
    @GetMapping("/problems")
    public ResponseEntity<Object> getAllProblems() {
        return ResponseEntity.ok(adminService.getAllProblems());
    }

    @org.springframework.web.bind.annotation.PostMapping("/problems")
    public ResponseEntity<Object> createProblem(@org.springframework.web.bind.annotation.RequestBody com.careercompass.ai.model.Problem problem) {
        return ResponseEntity.ok(adminService.createProblem(problem));
    }

    @org.springframework.web.bind.annotation.PutMapping("/problems/{id}")
    public ResponseEntity<Object> updateProblem(@org.springframework.web.bind.annotation.PathVariable Long id, @org.springframework.web.bind.annotation.RequestBody com.careercompass.ai.model.Problem problem) {
        return ResponseEntity.ok(adminService.updateProblem(id, problem));
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/problems/{id}")
    public ResponseEntity<Object> deleteProblem(@org.springframework.web.bind.annotation.PathVariable Long id) {
        adminService.deleteProblem(id);
        return ResponseEntity.ok().build();
    }

    // Courses
    @GetMapping("/courses")
    public ResponseEntity<Object> getAllCourses() {
        return ResponseEntity.ok(adminService.getAllCourses());
    }

    @org.springframework.web.bind.annotation.PostMapping("/courses")
    public ResponseEntity<Object> createCourse(@org.springframework.web.bind.annotation.RequestBody com.careercompass.ai.model.Course course) {
        return ResponseEntity.ok(adminService.createCourse(course));
    }

    @org.springframework.web.bind.annotation.PutMapping("/courses/{id}")
    public ResponseEntity<Object> updateCourse(@org.springframework.web.bind.annotation.PathVariable Long id, @org.springframework.web.bind.annotation.RequestBody com.careercompass.ai.model.Course course) {
        return ResponseEntity.ok(adminService.updateCourse(id, course));
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/courses/{id}")
    public ResponseEntity<Object> deleteCourse(@org.springframework.web.bind.annotation.PathVariable Long id) {
        adminService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }
}
