package com.hrms.employee.management.utility;

import com.hrms.employee.management.dto.EmployeeDto;
import com.hrms.employee.management.dto.OnboardKeycloakUserRequest;

public class EmployeeOnboardingHelper {

    public static OnboardKeycloakUserRequest getOnboardKeycloakUserRequest(EmployeeDto employeeDto) {
        return OnboardKeycloakUserRequest.builder().
                password(employeeDto.getPassword()).
                userName(employeeDto.getUsername()).
                email(employeeDto.getEmail()).
                realmName(employeeDto.getName()).
                temporaryPassword(employeeDto.isTemporaryPassword()).
                build();
    }
}
