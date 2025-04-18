package com.hrms.employee.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hrms.employee.management.dao.Employee;
import com.hrms.employee.management.dao.LeaveTracker;
import com.hrms.employee.management.dto.LeaveTrackerDto;
import com.hrms.employee.management.repository.EmployeeRepository;
import com.hrms.employee.management.repository.LeaveTrackerRepository;

@Service
public class LeaveTrackerServiceImpl implements LeaveTrackerService {

    private final LeaveTrackerRepository leaveTrackerRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveTrackerServiceImpl(LeaveTrackerRepository leaveTrackerRepository, EmployeeRepository employeeRepository) {
        this.leaveTrackerRepository = leaveTrackerRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public LeaveTracker applyLeave(String employeeId, LeaveTrackerDto leaveTrackerDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LeaveTracker leaveTracker = new LeaveTracker();
        leaveTracker.setEmployee(employee);
        leaveTracker.setStartDate(leaveTrackerDto.getStartDate());
        leaveTracker.setEndDate(leaveTrackerDto.getEndDate());
        leaveTracker.setLeaveType(leaveTrackerDto.getLeaveType());
        leaveTracker.setStatus("Pending");
        leaveTracker.setReason(leaveTracker.getReason());

        return leaveTrackerRepository.save(leaveTracker);
    }

    @Override
    public List<LeaveTracker> getLeaveHistory(String employeeId) {
        return leaveTrackerRepository.findByEmployee_EmployeeId(employeeId);
    }
}
