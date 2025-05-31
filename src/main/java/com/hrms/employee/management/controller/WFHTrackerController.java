package com.hrms.employee.management.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cglib.core.Local;
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
import org.springframework.web.bind.annotation.RequestParam;


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
        WFHTrackerResponse wfhTracker = wfhService.applyWFH(employeeId, wfmTrackerDto);
        return ResponseEntity.ok(wfhTracker);
    }
    @GetMapping("/{id}")
    public ResponseEntity<WFHTrackerResponse> getWFHDetailsById(@PathVariable String employeeId, @PathVariable Long id) {
        WFHTrackerResponse wfhTracker = wfhService.getWFHDetailsById(employeeId, id);
        return ResponseEntity.ok(wfhTracker);
    }

    @GetMapping
    public ResponseEntity<List<WFHTrackerResponse>> getWFHHistory(@PathVariable String employeeId) {
        List<WFHTrackerResponse> wfhTrackers = wfhService.getWFHHistory(employeeId);
        return ResponseEntity.ok(wfhTrackers);
    }
    @GetMapping("/date")
    public ResponseEntity<WFHTrackerResponse> getWFHByDate(@PathVariable String employeeId, @RequestParam LocalDate date) {
        WFHTrackerResponse wfhTracker = wfhService.getWFHByDate(employeeId,date);
        return ResponseEntity.ok(wfhTracker);
    }
}
