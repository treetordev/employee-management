package com.hrms.employee.management.service;

import java.util.List;

import com.hrms.employee.management.dao.LeaveTracker;
import com.hrms.employee.management.dto.LeaveTrackerDto;

public interface LeaveTrackerService {
    LeaveTracker applyLeave(String employeeId, LeaveTrackerDto leaveTrackerDto);
    List<LeaveTracker> getLeaveHistory(String employeeId);
}
