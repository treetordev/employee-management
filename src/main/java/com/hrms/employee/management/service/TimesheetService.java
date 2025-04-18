package com.hrms.employee.management.service;

import java.util.List;

import com.hrms.employee.management.dto.TimesheetDto;

public interface TimesheetService {
	TimesheetDto logWork(String employeeId, TimesheetDto timesheetDto);
    List<TimesheetDto> getTimesheetByEmployeeId(String employeeId);
}

