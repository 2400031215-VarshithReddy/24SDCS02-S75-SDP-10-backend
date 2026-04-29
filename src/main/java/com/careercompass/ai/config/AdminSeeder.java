package com.careercompass.ai.config;

import com.careercompass.ai.model.Role;
import com.careercompass.ai.model.User;
import com.careercompass.ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Seeds the admin user on application startup if it does not already exist.
 * Admin email: shannu1@gmail.com
 */
@Configuration
@RequiredArgsConstructor
@SuppressWarnings("null")
public class AdminSeeder {

    private static final Logger log = LoggerFactory.getLogger(AdminSeeder.class);

    private static final String ADMIN_EMAIL = "shannu1@gmail.com";
    private static final String ADMIN_NAME = "Admin";
    private static final String ADMIN_DEFAULT_PASSWORD = "admin@123"; // Change in production!

    @Bean
    public CommandLineRunner seedAdmin(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            var existing = userRepository.findByEmail(ADMIN_EMAIL);
            if (existing.isPresent()) {
                // Ensure the existing user has ADMIN role
                User user = existing.get();
                if (user.getRole() != Role.ADMIN) {
                    user.setRole(Role.ADMIN);
                    user.setEmailVerified(true);
                    userRepository.save(user);
                    log.info("╔══════════════════════════════════════════════════════╗");
                    log.info("║  EXISTING USER PROMOTED TO ADMIN                   ║");
                    log.info("║  Email: {}                                         ", ADMIN_EMAIL);
                    log.info("╚══════════════════════════════════════════════════════╝");
                }
                return;
            }

            User admin = User.builder()
                    .name(ADMIN_NAME)
                    .email(ADMIN_EMAIL)
                    .password(encoder.encode(ADMIN_DEFAULT_PASSWORD))
                    .role(Role.ADMIN)
                    .emailVerified(true) // Admin is pre-verified
                    .failedLoginAttempts(0)
                    .mfaEnabled(false)
                    .tokenVersion(0)
                    .build();

            userRepository.save(admin);
            log.info("╔══════════════════════════════════════════════════════╗");
            log.info("║  ADMIN USER CREATED                                ║");
            log.info("║  Email    : {}                                     ", ADMIN_EMAIL);
            log.info("║  Password : {}                                     ", ADMIN_DEFAULT_PASSWORD);
            log.info("║  ⚠  CHANGE THIS PASSWORD IN PRODUCTION!            ║");
            log.info("╚══════════════════════════════════════════════════════╝");
        };
    }
}
