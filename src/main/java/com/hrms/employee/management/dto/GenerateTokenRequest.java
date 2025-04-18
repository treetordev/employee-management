package com.hrms.employee.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateTokenRequest {
    private String password;
    private String username;
    private String clientId;
    private String clientSecret;
}
