package com.hrms.HRMS.employee.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import com.hrms.HRMS.employee.dao.LocationLog;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationLogResponse {
    private String employeeId;
    private LocalDate date;
    private List<LocationLog> logs;
    private Duration totalTimeInOffice;
    private String error;
}