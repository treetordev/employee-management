package com.hrms.employee.management.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LeaveTrackerDto {
    private Long leaveId;
    private String employeeId;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalDays;
    private String reason;
}

