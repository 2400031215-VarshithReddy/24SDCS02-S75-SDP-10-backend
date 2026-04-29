package com.careercompass.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String message;
    private String role;
    private String token; // NEW field for JWT
    /** true when the user has MFA enabled and must complete a second step */
    @Builder.Default
    private boolean mfaRequired = false;
}
