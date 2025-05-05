package com.hrms.employee.management.dto;

import lombok.Data;

@Data
public class SummaryDto {

    private int totalDays;
    private int filledDays;
    private int pendingApprovalDays;
    private int approvedDays;
    private int leaveDays;
    private int remainingDays;

}
