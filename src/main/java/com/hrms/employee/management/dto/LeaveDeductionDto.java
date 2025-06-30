package com.hrms.employee.management.dto;


import lombok.Data;

@Data
public class LeaveDeductionDto {
    
    private String leaveType;
    private int days;
    private String reason;
}
