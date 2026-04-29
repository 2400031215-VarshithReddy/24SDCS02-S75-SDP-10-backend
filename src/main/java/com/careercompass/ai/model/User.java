package com.careercompass.ai.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    private int xp = 0;

    @Builder.Default
    private int level = 1;

    private LocalDateTime createdAt;

    // ── Security fields ──

    @Builder.Default
    private boolean emailVerified = true;

    @Column(length = 64)
    private String verificationToken;

    @Builder.Default
    private int failedLoginAttempts = 0;

    private LocalDateTime accountLockedUntil;

    @Builder.Default
    private boolean mfaEnabled = false;

    @Column(length = 6)
    private String mfaSecret; // stored current OTP for demo

    @Column(length = 64)
    private String resetPasswordToken;

    private LocalDateTime resetPasswordTokenExpiry;

    /** Monotonically increasing version — tokens issued before this are invalid */
    @Builder.Default
    private long tokenVersion = 0;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ── UserDetails contract ──

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() {
        if (accountLockedUntil == null) return true;
        return LocalDateTime.now().isAfter(accountLockedUntil);
    }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
