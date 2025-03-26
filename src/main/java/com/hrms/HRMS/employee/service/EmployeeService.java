package com.hrms.HRMS.employee.service;

import java.util.List;

import com.hrms.HRMS.employee.dao.Employee;
import com.hrms.HRMS.employee.dto.EmployeeCountDto;
import com.hrms.HRMS.employee.dto.EmployeeDto;

public interface EmployeeService {
    Employee createEmployee(EmployeeDto employeeDto);
    Employee updateEmployee(String employeeId, EmployeeDto employeeDto);
    Employee getEmployeeById(String employeeId);
    List<Employee> getAllEmployees();
    EmployeeCountDto getEmployeeCounts();
}
