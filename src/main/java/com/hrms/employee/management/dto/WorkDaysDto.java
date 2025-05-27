package com.hrms.employee.management.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class WorkDaysDto {

    private LocalDate date;
    private String dayOfWeek;
    private String status;
    private TimesheetDto timesheetDto;
    private LeaveTrackerDto leaveTrackerDto;
}
