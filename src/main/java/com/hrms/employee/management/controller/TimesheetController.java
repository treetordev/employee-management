package com.hrms.employee.management.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hrms.employee.management.dto.DailyTimesheetDto;
import com.hrms.employee.management.dto.TimesheetDto;
import com.hrms.employee.management.service.TimesheetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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

    @GetMapping("/monthly")
    public ResponseEntity<List<TimesheetDto>> getMonthlyTimesheet(@PathVariable String employeeId, @RequestParam int month,@RequestParam int year) {
        List<TimesheetDto> monthlyTimesheet = timesheetService.getMonthlyTimesheet(employeeId, month,year);
        return ResponseEntity.ok(monthlyTimesheet);
    }
 
    @GetMapping("/daily")
    public ResponseEntity<DailyTimesheetDto> getDailyTimesheet(@PathVariable String employeeId, @RequestParam LocalDate date) {
        DailyTimesheetDto dailyTimesheet = timesheetService.getDailyTimesheet(employeeId, date);
        return ResponseEntity.ok(dailyTimesheet);
    }



    @PutMapping("/clock")
    public ResponseEntity<TimesheetDto> clockIn(@PathVariable String employeeId, @RequestBody TimesheetDto timesheetDto) {
        TimesheetDto result = timesheetService.clock(employeeId, timesheetDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    
}
