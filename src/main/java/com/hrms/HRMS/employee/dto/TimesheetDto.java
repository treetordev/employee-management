package com.hrms.HRMS.employee.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class TimesheetDto {
    private Long timesheetId;
    private String employeeId;
    private LocalDate workDate;
    private LocalTime clockIn;
    private LocalTime clockOut;
    private double totalHours;
}

