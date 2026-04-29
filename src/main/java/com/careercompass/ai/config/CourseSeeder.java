package com.careercompass.ai.config;

import com.careercompass.ai.model.Course;
import com.careercompass.ai.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@SuppressWarnings("null")
public class CourseSeeder implements CommandLineRunner {

    private final CourseRepository courseRepository;

    public void run(String... args) {
        if (courseRepository.count() == 0) {
            seedCourses();
        }
    }

    private void seedCourses() {
        courseRepository.save(Course.builder()
                .title("Advanced System Design")
                .description(
                        "Master the art of building scalable, fault-tolerant distributed systems using modern architectural patterns.")
                .difficulty("Advanced")
                .durationHours(40)
                .topics("Microservices, Scalability, Fault Tolerance, Distributed Systems")
                .trending(true)
                .build());

        courseRepository.save(Course.builder()
                .title("Neural Network Architectures")
                .description(
                        "Deep dive into the mathematical foundations and architectural design of modern neural networks.")
                .difficulty("Intermediate")
                .durationHours(35)
                .topics("Deep Learning, PyTorch, CNNs, Transformers")
                .trending(true)
                .build());

        courseRepository.save(Course.builder()
                .title("Cloud Infrastructure Mastery")
                .description(
                        "Learn to design and deploy complex cloud infrastructures using AWS, GCP, and Azure with IaC principles.")
                .difficulty("Intermediate")
                .durationHours(30)
                .topics("AWS, Terraform, Kubernetes, DevOps")
                .trending(false)
                .build());

        courseRepository.save(Course.builder()
                .title("Cybersecurity Defense Node")
                .description(
                        "Architect secure systems and implement proactive defense mechanisms against sophisticated cyber threats.")
                .difficulty("Advanced")
                .durationHours(45)
                .topics("Network Security, Ethical Hacking, Cryptography")
                .trending(true)
                .build());

        System.out.println("✅ Courses Seeded.");
    }
}
