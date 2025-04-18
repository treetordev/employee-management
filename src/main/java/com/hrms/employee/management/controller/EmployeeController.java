package com.hrms.employee.management.controller;

import java.util.List;

import com.hrms.employee.management.utility.TenantContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hrms.employee.management.dao.Employee;
import com.hrms.employee.management.dto.EmployeeCountDto;
import com.hrms.employee.management.dto.EmployeeDto;
import com.hrms.employee.management.service.EmployeeService;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins ="*")
public class EmployeeController {
	private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee createdEmployee = employeeService.createEmployee(employeeDto);
        String userId=employeeService.onboardUserInKeycloak(employeeDto, TenantContext.getCurrentTenant());
        return ResponseEntity.ok(createdEmployee);
    }


    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String employeeId, @RequestBody EmployeeDto employeeDto) {
        Employee updatedEmployee = employeeService.updateEmployee(employeeId, employeeDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
    
    @GetMapping("/counts")
    public ResponseEntity<EmployeeCountDto> getEmployeeCounts() {
        EmployeeCountDto employeeCounts = employeeService.getEmployeeCounts();
        return ResponseEntity.ok(employeeCounts);
    }

}
