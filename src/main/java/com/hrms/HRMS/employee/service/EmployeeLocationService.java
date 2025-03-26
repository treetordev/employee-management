package com.hrms.HRMS.employee.service;

import java.time.LocalDateTime;
import java.util.List;

import com.hrms.HRMS.employee.dao.LocationLog;
import com.hrms.HRMS.employee.exceptions.DeviceLocationException;
import com.hrms.HRMS.employee.exceptions.EmployeeNotFoundException;

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
