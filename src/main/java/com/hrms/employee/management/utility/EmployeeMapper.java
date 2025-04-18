package com.hrms.employee.management.utility;

import com.hrms.employee.management.dto.GenerateTokenRequest;
import org.springframework.stereotype.Component;

import com.hrms.employee.management.dao.Employee;
import com.hrms.employee.management.dto.EmployeeDto;


import java.util.Map;

@Component
public class EmployeeMapper {

    public static GenerateTokenRequest getGenerateTokenRequest(Map<String, Object> masterRealmDetails) {
        return GenerateTokenRequest.builder().
                password(masterRealmDetails.getPassword()).
                clientId(masterRealmDetails.getClientId()).
                username(masterRealmDetails.getUsername()).
                clientSecret(masterRealmDetails.getClientSecret()).
                build();
    }

    public Employee toEntity(EmployeeDto dto) {
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setUsername(dto.getUsername());
        employee.setRole(dto.getRole());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setAddress(dto.getAddress());
        employee.setCity(dto.getCity());
        employee.setState(dto.getState());
        employee.setZipCode(dto.getZipCode());
        employee.setCountry(dto.getCountry());
        employee.setJobTitle(dto.getJobTitle());
        employee.setProject(dto.getProject());
        employee.setJobType(dto.getJobType());
        employee.setJobStatus(dto.getJobStatus());
        employee.setJobDescription(dto.getJobDescription());
        return employee;
    }

    public void updateEntity(Employee employee, EmployeeDto dto) {
        employee.setName(dto.getName());
        employee.setUsername(dto.getUsername());
        employee.setRole(dto.getRole());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setAddress(dto.getAddress());
        employee.setCity(dto.getCity());
        employee.setState(dto.getState());
        employee.setZipCode(dto.getZipCode());
        employee.setCountry(dto.getCountry());
        employee.setJobTitle(dto.getJobTitle());
        employee.setProject(dto.getProject());
        employee.setJobType(dto.getJobType());
        employee.setJobStatus(dto.getJobStatus());
        employee.setJobDescription(dto.getJobDescription());
    }
}