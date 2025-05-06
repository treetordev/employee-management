package com.hrms.employee.management.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyTimesheetDto {

    private LocalDate date;
    private String dayOfWeek;
    private EmployeeTimeSheetDto employee;
    private String timesheetStatus;
    private TimesheetDto timesheet;
    private boolean editable;
    private boolean leaveDay;
    private boolean weekend;
    private boolean holiday;
    private boolean futureDate;
    private LeaveTrackerDto leaveTracker;
    private String message;
}
