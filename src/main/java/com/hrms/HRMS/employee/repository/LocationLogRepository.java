package com.hrms.HRMS.employee.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.HRMS.employee.dao.Employee;
import com.hrms.HRMS.employee.dao.LocationLog;

public interface LocationLogRepository extends JpaRepository<LocationLog, Long> {
    List<LocationLog> findByEmployeeAndTimestampBetween(
        Employee employee, 
        LocalDateTime start, 
        LocalDateTime end
    );
}
