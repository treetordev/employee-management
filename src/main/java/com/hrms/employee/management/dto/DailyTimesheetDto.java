package com.hrms.employee.management.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DailyTimesheetDto {

    private LocalDate date;
    private String dayOfWeek;
    private EmployeeTimeSheetDto employee;
    private String timesheetStatus;
    private TimesheetDto timesheet;
    private boolean isEditable;
    private boolean isLeaveDay;
    private boolean isWeekend;
    private boolean isHoliday;  
    private boolean isFutureDate;
    private LeaveTrackerDto leaveTracker;
    private String message;
}
