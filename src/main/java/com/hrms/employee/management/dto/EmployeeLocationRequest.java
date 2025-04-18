package com.hrms.employee.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLocationRequest {
    @NotNull(message = "Employee ID is required")
    private String employeeId;

    @NotBlank(message = "Device IP Address is required")
    private String deviceIpAddress;
}