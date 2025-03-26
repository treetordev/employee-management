package com.hrms.HRMS.employee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hrms.HRMS.employee.dto.TimesheetDto;
import com.hrms.HRMS.employee.service.TimesheetService;

@RestController
@RequestMapping("/employees/{employeeId}/timesheets")
@CrossOrigin(origins ="*")
public class TimesheetController {

    private final TimesheetService timesheetService;

    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @PostMapping
    public ResponseEntity<TimesheetDto> addTimesheet(@PathVariable String employeeId, @RequestBody TimesheetDto timesheetDto) {
        TimesheetDto savedTimesheet = timesheetService.logWork(employeeId, timesheetDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTimesheet);
    }

    @GetMapping
    public ResponseEntity<List<TimesheetDto>> getTimesheetHistory(@PathVariable String employeeId) {
        List<TimesheetDto> timesheetHistory = timesheetService.getTimesheetByEmployeeId(employeeId);
        return ResponseEntity.ok(timesheetHistory);
    }
}
