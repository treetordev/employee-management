package com.hrms.employee.management.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class WFHTrackerRequest {

    private String employeeId;
    private String wfhCreditOption;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}
