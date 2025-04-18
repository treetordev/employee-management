package com.hrms.employee.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeCountDto {
    private long totalEmployees;
    private long activeEmployees;
}
