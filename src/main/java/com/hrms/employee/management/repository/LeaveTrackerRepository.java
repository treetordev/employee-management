package com.hrms.employee.management.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hrms.employee.management.dao.LeaveTracker;

public interface LeaveTrackerRepository extends JpaRepository<LeaveTracker, Long> {
    List<LeaveTracker> findByEmployee_EmployeeId(String employeeId);

    @Query("SELECT l FROM LeaveTracker l WHERE l.employee.employeeId = :employeeId AND :date BETWEEN l.startDate AND l.endDate")
    LeaveTracker findLeaveByDateAndEmployeeId(@Param("date") LocalDate date, @Param("employeeId") String employeeId);

}
