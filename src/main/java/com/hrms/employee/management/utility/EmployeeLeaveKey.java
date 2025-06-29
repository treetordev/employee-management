package com.hrms.employee.management.utility;

import lombok.Data;

@Data
public class EmployeeLeaveKey {
    
    private String employeeId;
    private String leaveTypeName;

    public EmployeeLeaveKey(String employeeId2, String leaveTypeName2) {
        this.employeeId = employeeId2;
        this.leaveTypeName = leaveTypeName2;
    }

}
