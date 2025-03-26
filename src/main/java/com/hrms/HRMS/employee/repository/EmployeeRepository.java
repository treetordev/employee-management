package com.hrms.HRMS.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.HRMS.employee.dao.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
	long countByJobStatus(String jobStatus);
}
