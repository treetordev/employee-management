package com.hrms.employee.management.service;

import java.util.List;

import com.hrms.employee.management.dao.Employee;
import com.hrms.employee.management.dto.EmployeeCountDto;
import com.hrms.employee.management.dto.EmployeeDto;

public interface EmployeeService {
    Employee createEmployee(EmployeeDto employeeDto);
    Employee updateEmployee(String employeeId, EmployeeDto employeeDto);
    Employee getEmployeeById(String employeeId);
    List<Employee> getAllEmployees();
    EmployeeCountDto getEmployeeCounts();

    String onboardUserInKeycloak(EmployeeDto employeeDto, String currentTenant);


    List<Employee> findUnassignedEmployees();

    List<Employee> findEmployeesByGroup(Long groupId);

    void assignGroupToEmployee(String employeeId, Long groupId);

    void assignManagerToEmployee(String employeeId, String managerEmpId);
}
