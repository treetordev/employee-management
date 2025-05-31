package com.hrms.employee.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrms.employee.management.dao.WFHTracker;

@Repository
public interface WFHTrackerRepository extends JpaRepository<WFHTracker, Long> {

    List<WFHTracker> findAllByEmployee_EmployeeId(String employeeId);

}
