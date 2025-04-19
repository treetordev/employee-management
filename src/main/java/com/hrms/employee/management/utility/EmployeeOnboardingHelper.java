package com.hrms.employee.management.utility;

import com.hrms.employee.management.dto.EmployeeDto;
import com.hrms.employee.management.dto.OnboardKeycloakUserRequest;

public class EmployeeOnboardingHelper {

    public static OnboardKeycloakUserRequest getOnboardKeycloakUserRequest(EmployeeDto employeeDto, String currentTenant) {
        return OnboardKeycloakUserRequest.builder().
                password(employeeDto.getPassword()).
                userName(employeeDto.getUsername()).
                email(employeeDto.getEmail()).
                realmName(currentTenant).
                temporaryPassword(employeeDto.isTemporaryPassword()).
                build();
    }
}
