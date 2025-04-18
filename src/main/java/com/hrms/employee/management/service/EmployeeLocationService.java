package com.hrms.employee.management.service;

import java.time.LocalDateTime;
import java.util.List;

import com.hrms.employee.management.dao.LocationLog;
import com.hrms.employee.management.exceptions.DeviceLocationException;
import com.hrms.employee.management.exceptions.EmployeeNotFoundException;

public interface EmployeeLocationService {
    boolean validateEmployeeLocation(String employeeId, String deviceIpAddress) 
        throws EmployeeNotFoundException, DeviceLocationException;
    
    LocationLog logEmployeeCheckIn(String employeeId, String deviceIpAddress) 
        throws EmployeeNotFoundException;
    
    List<LocationLog> getEmployeeDailyLocationLogs(
        String employeeId, 
        LocalDateTime date
    ) throws EmployeeNotFoundException;
}
