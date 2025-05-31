package com.hrms.employee.management.service;

import java.time.LocalDate;
import java.util.List;

import com.hrms.employee.management.dto.WFHTrackerRequest;
import com.hrms.employee.management.dto.WFHTrackerResponse;

public interface WFHService {

    WFHTrackerResponse applyWFH(String employeeId, WFHTrackerRequest wfhTrackerRequest);
    List<WFHTrackerResponse> getWFHHistory(String employeeId);
    WFHTrackerResponse getWFHDetailsById(String employeeId, Long id);
    WFHTrackerResponse getWFHByDate(String employeeId, LocalDate date);


}
