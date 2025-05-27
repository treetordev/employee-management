package com.hrms.employee.management.dto;

import lombok.Data;

@Data
public class SummaryDto {
    private int totalWorkDays;
    private int totalPresentDays;
    private int totalAbsentDays;
    private int totalLeaveDays;
    private int totalWfhDays;
    private Double totalWorkingHours;
    private Double totalRegularHours;
    private Double totalOvertimeHours;

}