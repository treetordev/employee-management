package com.hrms.employee.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.employee.management.dao.LeaveTracker;

public interface LeaveTrackerRepository extends JpaRepository<LeaveTracker, Long> {
    List<LeaveTracker> findByEmployee_EmployeeId(String employeeId);
}
