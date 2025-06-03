package com.hrms.employee.management.dto;

import lombok.Data;

@Data
public class LeaveBalanceDto {
    private String leaveTypeId;
    private String leaveTypeName;
    private int allocatedDays;
    private int usedDays;
    private int carryForwardDays;
    private int remainingDays;
    private int year;
}