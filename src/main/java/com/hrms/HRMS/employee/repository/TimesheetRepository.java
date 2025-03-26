package com.hrms.HRMS.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.HRMS.employee.dao.Timesheet;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
    List<Timesheet> findByEmployee_EmployeeId(String employeeId);
}
