package com.hrms.HRMS.employee.service;

import java.util.List;

import com.hrms.HRMS.employee.dao.LeaveTracker;
import com.hrms.HRMS.employee.dto.LeaveTrackerDto;

public interface LeaveTrackerService {
    LeaveTracker applyLeave(String employeeId, LeaveTrackerDto leaveTrackerDto);
    List<LeaveTracker> getLeaveHistory(String employeeId);
}
