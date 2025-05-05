package com.hrms.employee.management.service;

import java.time.LocalDate;
import java.util.List;

import com.hrms.employee.management.dto.DailyTimesheetDto;
import com.hrms.employee.management.dto.TimesheetDto;

public interface TimesheetService {
	TimesheetDto logWork(String employeeId, TimesheetDto timesheetDto);
    List<TimesheetDto> getTimesheetByEmployeeId(String employeeId);
    
    TimesheetDto clock(String employeeId, TimesheetDto timesheetDto);
    
    List<TimesheetDto> getMonthlyTimesheet(String employeeId, int month,int year);
    DailyTimesheetDto getDailyTimesheet(String employeeId, LocalDate date);
}

