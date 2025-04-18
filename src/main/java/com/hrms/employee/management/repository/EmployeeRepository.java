package com.hrms.employee.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.employee.management.dao.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
	long countByJobStatus(String jobStatus);
}
