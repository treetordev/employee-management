package com.hrms.employee.management.repository;

import com.hrms.employee.management.dao.LeaveTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveTransactionRepository extends JpaRepository<LeaveTransaction, Long> {
    // List<LeaveTransaction> findByEmployeeIdOrderByCreatedAtDesc(String employeeId);
    // List<LeaveTransaction> findByEmployeeIdAndLeaveTypeIdOrderByCreatedAtDesc(String employeeId, String leaveTypeId);
}
