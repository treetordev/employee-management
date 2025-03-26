package com.hrms.HRMS.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.HRMS.employee.dao.LeaveTracker;

public interface LeaveTrackerRepository extends JpaRepository<LeaveTracker, Long> {
    List<LeaveTracker> findByEmployee_EmployeeId(String employeeId);
}
