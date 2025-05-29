package com.hrms.employee.management.dto;

import lombok.Data;

@Data
public class WorkDaysDto {

    private String date;
    private String dayOfWeek;
    private String status;
    private TimesheetDto timesheetDto;
    private LeaveTrackerDto leaveTrackerDto;
}
