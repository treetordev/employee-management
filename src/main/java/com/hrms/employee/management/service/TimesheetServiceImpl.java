package com.hrms.employee.management.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrms.employee.management.dao.Employee;
import com.hrms.employee.management.dao.Timesheet;
import com.hrms.employee.management.dto.TimesheetDto;
import com.hrms.employee.management.repository.EmployeeRepository;
import com.hrms.employee.management.repository.TimesheetRepository;

@Service
public class TimesheetServiceImpl implements TimesheetService {

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public TimesheetDto logWork(String employeeId, TimesheetDto timesheetDto) {
        // Fetch the employee
        Employee employee = employeeRepository.findById(employeeId)
                                              .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Create Timesheet entry
        Timesheet timesheet = new Timesheet();
        timesheet.setEmployee(employee);
        timesheet.setWorkDate(timesheetDto.getWorkDate());
        timesheet.setClockIn(timesheetDto.getClockIn());
        timesheet.setClockOut(timesheetDto.getClockOut());

        // Calculate total hours
        Duration duration = Duration.between(timesheetDto.getClockIn(), timesheetDto.getClockOut());
        timesheet.setTotalHours(duration.toHours() + (duration.toMinutesPart() / 60.0));

        // Save the entity
        Timesheet savedTimesheet = timesheetRepository.save(timesheet);

        // Convert and return DTO
        return convertToDto(savedTimesheet);
    }

    @Override
    public List<TimesheetDto> getTimesheetByEmployeeId(String employeeId) {
        List<Timesheet> timesheets = timesheetRepository.findByEmployee_EmployeeId(employeeId);
        return timesheets.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private TimesheetDto convertToDto(Timesheet timesheet) {
        TimesheetDto dto = new TimesheetDto();
        dto.setTimesheetId(timesheet.getId());
        dto.setEmployeeId(timesheet.getEmployee().getEmployeeId());
        dto.setWorkDate(timesheet.getWorkDate());
        dto.setClockIn(timesheet.getClockIn());
        dto.setClockOut(timesheet.getClockOut());
        dto.setTotalHours(timesheet.getTotalHours());
        return dto;
    }
}