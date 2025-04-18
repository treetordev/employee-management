package com.hrms.employee.management.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hrms.employee.management.dao.Employee;
import com.hrms.employee.management.dao.LocationLog;
import com.hrms.employee.management.exceptions.DeviceLocationException;
import com.hrms.employee.management.exceptions.EmployeeNotFoundException;
import com.hrms.employee.management.repository.EmployeeRepository;
import com.hrms.employee.management.repository.LocationLogRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeLocationServiceImpl implements EmployeeLocationService {
    private final EmployeeRepository employeeRepository;
    private final LocationLogRepository locationLogRepository;
    private final IpLocationValidator ipLocationValidator;

    @Override
    @Transactional
    public boolean validateEmployeeLocation(
        String employeeId, 
        String deviceIpAddress
    ) throws EmployeeNotFoundException, DeviceLocationException {
        // Check if employee exists
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new EmployeeNotFoundException(
                "Employee with ID " + employeeId + " not found"
            ));

        // Validate device IP against office location
        try {
            return ipLocationValidator.isInOfficeLocation(deviceIpAddress);
        } catch (Exception e) {
            throw new DeviceLocationException(
                "Unable to validate device location: " + e.getMessage()
            );
        }
    }
    
    @Override
    @Transactional
    public LocationLog logEmployeeCheckIn(
        String employeeId, 
        String deviceIpAddress
    ) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new EmployeeNotFoundException(
                "Employee with ID " + employeeId + " not found"
            ));

        LocationLog checkInLog = LocationLog.builder()
            .employee(employee)
            .deviceIpAddress(deviceIpAddress)
            .timestamp(LocalDateTime.now())
            .logType(LocationLog.LogType.CHECK_IN)
            .build();

        return locationLogRepository.save(checkInLog);
    }

    @Override
    public List<LocationLog> getEmployeeDailyLocationLogs(
        String employeeId, 
        LocalDateTime date
    ) throws EmployeeNotFoundException {
        // Validate employee exists
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new EmployeeNotFoundException(
                "Employee with ID " + employeeId + " not found"
            ));

        // Find logs for the specific date
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        return locationLogRepository.findByEmployeeAndTimestampBetween(
            employee, startOfDay, endOfDay
        );
    }
}
