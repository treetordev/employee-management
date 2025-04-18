package com.hrms.employee.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.employee.management.dao.Timesheet;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
    List<Timesheet> findByEmployee_EmployeeId(String employeeId);
}
