package com.hrms.employee.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrms.employee.management.dto.WFHTrackerRequest;
import com.hrms.employee.management.dto.WFHTrackerResponse;
import com.hrms.employee.management.service.WFHService;

@RestController
@RequestMapping("/employees/{employeeId}/wfh")
@CrossOrigin(origins ="*")
public class WFHTrackerController {
    
    private final WFHService wfhService;

    public WFHTrackerController(WFHService wfhService) {
        this.wfhService = wfhService;
    }

    @PostMapping
    public ResponseEntity<WFHTrackerResponse> applyWFH(@PathVariable String employeeId, @RequestBody WFHTrackerRequest wfmTrackerDto) {
        WFHTrackerResponse leave = wfhService.applyWFH(employeeId, wfmTrackerDto);
        return ResponseEntity.ok(leave);
    }

    @GetMapping
    public ResponseEntity<List<WFHTrackerResponse>> getWFHHistory(@PathVariable String employeeId) {
        List<WFHTrackerResponse> leaveHistory = wfhService.getWFHHistory(employeeId);
        return ResponseEntity.ok(leaveHistory);
    }
}
