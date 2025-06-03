package com.hrms.employee.management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BulkLeaveAssignmentDto {
    @NotBlank(message = "Leave type ID is required")
    private String leaveTypeId;

    @Min(value = 0, message = "Days must be non-negative")
    private int days;

    private String reason;

    private boolean overrideExisting = false;
}
