package com.hrms.employee.management.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeReportResponse {

    private String employeeId;
    private String employeeName;
    private int year;
    private int month;
    private String monthName;
    private List<WorkDaysDto> workDays;
    private SummaryDto summary;
}
