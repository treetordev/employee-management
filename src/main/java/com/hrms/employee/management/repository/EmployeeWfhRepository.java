package com.hrms.employee.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrms.employee.management.dao.EmployeeWfhBalance;

@Repository
public interface EmployeeWfhRepository extends JpaRepository<EmployeeWfhBalance, Long> {

}
