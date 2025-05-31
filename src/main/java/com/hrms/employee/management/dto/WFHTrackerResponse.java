package com.hrms.employee.management.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WFHTrackerResponse {

    private Long id;
    private EmployeeDto employee;
    private String wfhCreditOption;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status;
    private String createdAt;
    private String updatedAt;


}
