package com.hrms.employee.management.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.employee.management.dao.Employee;
import com.hrms.employee.management.dao.LocationLog;

public interface LocationLogRepository extends JpaRepository<LocationLog, Long> {
    List<LocationLog> findByEmployeeAndTimestampBetween(
        Employee employee, 
        LocalDateTime start, 
        LocalDateTime end
    );
}
