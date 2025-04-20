package com.hrms.employee.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.employee.management.dao.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
	long countByJobStatus(String jobStatus);
	List<Employee> findByGroupIdIsNull();

	List<Employee> findByGroupId(Long groupId);
}
