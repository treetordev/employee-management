package com.hrms.HRMS.employee.service;

import java.util.List;

import com.hrms.HRMS.employee.dto.TimesheetDto;

public interface TimesheetService {
	TimesheetDto logWork(String employeeId, TimesheetDto timesheetDto);
    List<TimesheetDto> getTimesheetByEmployeeId(String employeeId);
}

