package com.hrms.employee.management.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LeaveDeductionDto {
    @NotBlank(message = "Leave type ID is required")
    private String leaveTypeId;

    @Min(value = 1, message = "Days to deduct must be at least 1")
    private int days;

    private String reason;
}
