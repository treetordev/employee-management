package com.hrms.HRMS.employee.service;

import com.hrms.HRMS.employee.exceptions.DeviceLocationException;

public interface IpLocationValidator {
    boolean isInOfficeLocation(String ipAddress) throws DeviceLocationException;
}
