package com.hrms.employee.management.utility;

import com.hrms.employee.management.dao.LeaveTracker;
import com.hrms.employee.management.dto.ActionItemExtRequest;

public class ActionItemHelper {
    public static ActionItemExtRequest convertToLeaveRequest(LeaveTracker leaveTracker, String employeeId, String assignedManagerId) {

        return ActionItemExtRequest.builder().
                initiatorUserId(employeeId).
                title("Leave Application Notification").
                type(ActionItemExtRequest.ActionType.LEAVE).
                assigneeUserId(assignedManagerId).
                referenceId(leaveTracker.getId()).
                build();

    }
}
