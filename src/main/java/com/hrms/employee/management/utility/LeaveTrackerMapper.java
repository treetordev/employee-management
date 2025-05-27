package com.hrms.employee.management.utility;

import org.springframework.stereotype.Component;

import com.hrms.employee.management.dao.LeaveTracker;
import com.hrms.employee.management.dto.LeaveTrackerDto;

@Component
public class LeaveTrackerMapper {
   
    public LeaveTrackerDto convertToDto(LeaveTracker leaveTracker) {
        LeaveTrackerDto dto = new LeaveTrackerDto();
        dto.setLeaveId(leaveTracker.getId());
        dto.setEmployeeId(leaveTracker.getEmployee().getEmployeeId());
        dto.setLeaveType(leaveTracker.getLeaveType());
        dto.setStartDate(leaveTracker.getStartDate());
        dto.setEndDate(leaveTracker.getEndDate());
        return dto;
    }

}
