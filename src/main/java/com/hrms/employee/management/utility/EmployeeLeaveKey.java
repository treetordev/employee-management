package com.hrms.employee.management.utility;

import lombok.Data;

@Data
public class EmployeeLeaveKey {
    private String employeeId;
    private String leaveName;

    public EmployeeLeaveKey(String employeeId, String leaveName) {
        this.employeeId = employeeId;
        this.leaveName = leaveName;
    }

}
