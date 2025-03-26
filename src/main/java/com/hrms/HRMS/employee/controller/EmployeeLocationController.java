package com.hrms.HRMS.employee.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hrms.HRMS.employee.dao.LocationLog;
import com.hrms.HRMS.employee.dto.EmployeeLocationRequest;
import com.hrms.HRMS.employee.dto.LocationLogResponse;
import com.hrms.HRMS.employee.exceptions.DeviceLocationException;
import com.hrms.HRMS.employee.exceptions.EmployeeNotFoundException;
import com.hrms.HRMS.employee.service.EmployeeLocationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employee-location")
@RequiredArgsConstructor
public class EmployeeLocationController {

    private final EmployeeLocationService employeeLocationService;

    @PostMapping("/check-in")
    public ResponseEntity<String> checkInEmployee(
        @Valid @RequestBody EmployeeLocationRequest request
    ) {
        try {
            // Validate and check employee location
            boolean isInOfficeLocation = employeeLocationService.validateEmployeeLocation(
                request.getEmployeeId(), 
                request.getDeviceIpAddress()
            );

            if (isInOfficeLocation) {
                // Log check-in
                LocationLog log = employeeLocationService.logEmployeeCheckIn(
                    request.getEmployeeId(), 
                    request.getDeviceIpAddress()
                );
                
                return ResponseEntity.ok("Welcome to office");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not in office location");
            }
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Employee not found: " + e.getMessage());
        } catch (DeviceLocationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Device location error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/daily-log/{employeeId}")
    public ResponseEntity<LocationLogResponse> getDailyEmployeeLocationLog(
        @PathVariable String employeeId, 
        @RequestParam(required = false) LocalDateTime specificDate
    ) {
        try {
            // If no date specified, use current date
            LocalDateTime dateToCheck = specificDate != null 
                ? specificDate 
                : LocalDateTime.now();

            // Retrieve logs for the employee on the specified date
            List<LocationLog> dailyLogs = employeeLocationService
                .getEmployeeDailyLocationLogs(employeeId, dateToCheck);

            // Calculate total time in office
            Duration totalTimeInOffice = calculateTotalTimeInOffice(dailyLogs);

            // Prepare response
            LocationLogResponse response = LocationLogResponse.builder()
                .employeeId(employeeId)
                .date(dateToCheck.toLocalDate())
                .logs(dailyLogs)
                .totalTimeInOffice(totalTimeInOffice)
                .build();

            return ResponseEntity.ok(response);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(LocationLogResponse.builder()
                    .error("Employee not found: " + e.getMessage())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(LocationLogResponse.builder()
                    .error("Unexpected error: " + e.getMessage())
                    .build());
        }
    }

    private Duration calculateTotalTimeInOffice(List<LocationLog> logs) {
        if (logs == null || logs.size() < 2) {
            return Duration.ZERO;
        }

        // Assuming logs are sorted by timestamp
        Duration totalTime = Duration.ZERO;
        for (int i = 0; i < logs.size(); i += 2) {
            if (i + 1 < logs.size()) {
                LocalDateTime checkIn = logs.get(i).getTimestamp();
                LocalDateTime checkOut = logs.get(i + 1).getTimestamp();
                totalTime = totalTime.plus(Duration.between(checkIn, checkOut));
            }
        }

        return totalTime;
    }
}
