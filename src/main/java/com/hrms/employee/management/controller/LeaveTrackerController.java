package com.hrms.employee.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hrms.employee.management.dao.LeaveTracker;
import com.hrms.employee.management.dto.LeaveTrackerDto;
import com.hrms.employee.management.service.LeaveTrackerService;

@RestController
@RequestMapping("/employees/{employeeId}/leave-tracker")
@CrossOrigin(origins ="*")
public class LeaveTrackerController {

    private final LeaveTrackerService leaveTrackerService;

    public LeaveTrackerController(LeaveTrackerService leaveTrackerService) {
        this.leaveTrackerService = leaveTrackerService;
    }

    @PostMapping
    public ResponseEntity<LeaveTracker> applyLeave(@PathVariable String employeeId, @RequestBody LeaveTrackerDto leaveTrackerDto) {
        LeaveTracker leave = leaveTrackerService.applyLeave(employeeId, leaveTrackerDto);
        return ResponseEntity.ok(leave);
    }

    @GetMapping
    public ResponseEntity<List<LeaveTracker>> getLeaveHistory(@PathVariable String employeeId) {
        List<LeaveTracker> leaveHistory = leaveTrackerService.getLeaveHistory(employeeId);
        return ResponseEntity.ok(leaveHistory);
    }
}
