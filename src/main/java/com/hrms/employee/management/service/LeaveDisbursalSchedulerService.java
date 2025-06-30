package com.hrms.employee.management.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hrms.employee.management.dao.Employee;
import com.hrms.employee.management.dao.EmployeeLeaveBalance;
import com.hrms.employee.management.dao.LeaveTransaction;
import com.hrms.employee.management.dto.LeaveDisbursalDto;
import com.hrms.employee.management.repository.EmployeeLeaveBalanceRepository;
import com.hrms.employee.management.repository.EmployeeRepository;
import com.hrms.employee.management.repository.LeaveTransactionRepository;
import com.hrms.employee.management.utility.EmployeeLeaveKey;
import com.hrms.employee.management.utility.LeaveTransactionType;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@EnableScheduling
public class LeaveDisbursalSchedulerService {

    @Value("${company.service.url}")
    private String companyServiceBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeLeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private LeaveTransactionRepository leaveTransactionRepository;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void disburseMonthlyLeave() {
        LeaveDisbursalDto[] leaveDisbursals = restTemplate.getForObject(
                companyServiceBaseUrl + "/leave-types/schedule/monthly",
                LeaveDisbursalDto[].class);
        if (leaveDisbursals.length == 0) {
            log.warn("No quarterly leave types found. Skipping disbursal.");
            return;
        }
        List<LeaveDisbursalDto> leaveDisbursal = Arrays.asList(leaveDisbursals);
        for (LeaveDisbursalDto leave : leaveDisbursal) {
            disburseLeave(leave.getName(), 12, leave);
        }

    }

    @Scheduled(cron = "0 0 0 1 1 ?")
    public void disburseYearlyLeave() {
        LeaveDisbursalDto[] leaveDisbursals = restTemplate.getForObject(
                companyServiceBaseUrl + "/leave-types/schedule/yearly",
                LeaveDisbursalDto[].class);

        if (leaveDisbursals.length == 0) {
            log.warn("No yearly leave types found. Skipping disbursal.");
            return;
        }

        List<LeaveDisbursalDto> leaveDisbursal = Arrays.asList(leaveDisbursals);
        for (LeaveDisbursalDto leave : leaveDisbursal) {
            disburseLeave(leave.getName(), 1, leave);
        }

    }

    @Scheduled(cron = "0 0 0 1 1,4,7,10 ?")
    public void disburseQuarterlyLeave() {
        LeaveDisbursalDto[] leaveDisbursals = restTemplate.getForObject(
                companyServiceBaseUrl + "/leave-types/schedule/quarterly",
                LeaveDisbursalDto[].class);
        if (leaveDisbursals.length == 0) {
            log.warn("No quarterly leave types found. Skipping disbursal.");
            return;
        }
        List<LeaveDisbursalDto> leaveDisbursal = Arrays.asList(leaveDisbursals);
        for (LeaveDisbursalDto leave : leaveDisbursal) {
            disburseLeave(leave.getName(), 4, leave);
        }

    }

    @Scheduled(cron = "0 0 0 1 1,7 ?")
    public void disburseHalfYearlyLeave() {
        LeaveDisbursalDto[] leaveDisbursals = restTemplate.getForObject(
                companyServiceBaseUrl + "/leave-types/schedule/half_yearly",
                LeaveDisbursalDto[].class);
        if (leaveDisbursals.length == 0) {
            log.warn("No half-yearly leave types found. Skipping disbursal.");
            return;
        }
        List<LeaveDisbursalDto> leaveDisbursal = Arrays.asList(leaveDisbursals);

        for (LeaveDisbursalDto leave : leaveDisbursal) {
            disburseLeave(leave.getName(), 2, leave);
        }

    }

    @Transactional
    public void disburseLeave(String leaveName, int divisor, LeaveDisbursalDto leaveDisbursal) {

        List<Employee> employees = employeeRepository.findAll();
        

        double daysToDisburse = leaveDisbursal.getTotalDays() / (double) divisor;
        daysToDisburse = Math.round(daysToDisburse * 100.0) / 100.0;

        List<EmployeeLeaveBalance> employeeLeaveBalances = leaveBalanceRepository.findAll();

        Map<EmployeeLeaveKey, EmployeeLeaveBalance> leaveBalanceMap = employeeLeaveBalances.stream()
                .filter(e->e.getLeaveTypeName().equals(leaveName))
                .collect(Collectors.toMap(
                        emp -> new EmployeeLeaveKey(emp.getEmployeeId(), leaveName),
                        emp -> emp));

        List<LeaveTransaction> leaveTransactions = new ArrayList<>();
        List<EmployeeLeaveBalance> updatedLeaveBalances = new ArrayList<>();
        int currentYear = java.time.Year.now().getValue();
        for (Employee employee : employees) {
            EmployeeLeaveKey employeeLeaveKey = new EmployeeLeaveKey(employee.getEmployeeId(), leaveName);
            EmployeeLeaveBalance balance = leaveBalanceMap.get(employeeLeaveKey);

            if (balance == null) {
                balance = new EmployeeLeaveBalance();
                balance.setEmployeeId(employee.getEmployeeId());
                balance.setLeaveTypeName(leaveName);
                balance.setLeaveBalance(0);
                balance.setYear(currentYear);
            }

            balance.setLeaveBalance(balance.getLeaveBalance() + daysToDisburse);
            updatedLeaveBalances.add(balance);

            LeaveTransaction transaction = new LeaveTransaction();
            transaction.setEmployeeId(employee.getEmployeeId());
            transaction.setLeaveTypeName(leaveName);
            transaction.setTransactionType(LeaveTransactionType.CREDIT);
            transaction.setDays(daysToDisburse);
            leaveTransactions.add(transaction);
        }

        leaveBalanceRepository.saveAll(updatedLeaveBalances);
        leaveTransactionRepository.saveAll(leaveTransactions);
    }

}