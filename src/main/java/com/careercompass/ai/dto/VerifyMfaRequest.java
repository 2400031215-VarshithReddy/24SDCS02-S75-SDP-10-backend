package com.careercompass.ai.dto;

import lombok.Data;

@Data
public class VerifyMfaRequest {
    private String email;
    private String otp;
}
