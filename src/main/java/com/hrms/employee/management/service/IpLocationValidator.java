package com.hrms.employee.management.service;

import com.hrms.employee.management.exceptions.DeviceLocationException;

public interface IpLocationValidator {
    boolean isInOfficeLocation(String ipAddress) throws DeviceLocationException;
}
