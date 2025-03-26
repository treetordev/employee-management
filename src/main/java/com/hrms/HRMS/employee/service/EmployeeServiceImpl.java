package com.hrms.HRMS.employee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hrms.HRMS.employee.dao.Employee;
import com.hrms.HRMS.employee.dto.EmployeeCountDto;
import com.hrms.HRMS.employee.dto.EmployeeDto;
import com.hrms.HRMS.employee.repository.EmployeeRepository;
import com.hrms.HRMS.employee.utility.EmployeeMapper;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Employee createEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(String employeeId, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeMapper.updateEntity(employee, employeeDto);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(String employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    @Override
    public EmployeeCountDto getEmployeeCounts() {
        long totalEmployees = employeeRepository.count();
        long activeEmployees = employeeRepository.countByJobStatus("Active");
        return new EmployeeCountDto(totalEmployees, activeEmployees);
    }
}