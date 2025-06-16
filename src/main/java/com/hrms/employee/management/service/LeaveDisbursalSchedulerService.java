package com.hrms.employee.management.service;

import java.util.ArrayList;
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
import com.hrms.employee.management.utility.DisbursalFrequency;
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
        LeaveDisbursalDto leaveDisbursal = restTemplate.getForObject(companyServiceBaseUrl + "/leave-types/monthly",
                LeaveDisbursalDto.class);
        if (leaveDisbursal == null) {
            log.warn("No monthly leave type found. Skipping disbursal.");
            System.out.println("No monthly leave type found. Skipping disbursal.");
        }

        disburseLeave(DisbursalFrequency.MONTHLY, 12,leaveDisbursal);
    }

    @Scheduled(cron = "0 0 0 1 1 ?")
    public void disburseYearlyLeave() {
        LeaveDisbursalDto leaveDisbursal = restTemplate.getForObject(companyServiceBaseUrl + "/leave-types/yearly",
                LeaveDisbursalDto.class);
        if (leaveDisbursal == null) {
            log.warn("No monthly leave type found. Skipping disbursal.");
            System.out.println("No monthly leave type found. Skipping disbursal.");
        }

        disburseLeave(DisbursalFrequency.YEARLY, 1,leaveDisbursal);
    }

    @Scheduled(cron = "0 0 0 1 1,4,7,10 ?")
    public void disburseQuarterlyLeave() {
        LeaveDisbursalDto leaveDisbursal = restTemplate.getForObject(companyServiceBaseUrl + "/leave-types/quarterly",
                LeaveDisbursalDto.class);
        if (leaveDisbursal == null) {
            log.warn("No monthly leave type found. Skipping disbursal.");
            System.out.println("No monthly leave type found. Skipping disbursal.");
        }

        disburseLeave(DisbursalFrequency.QUARTERLY, 4,leaveDisbursal);
    }

    @Scheduled(cron = "0 0 0 1 1,7 ?")
    public void disburseHalfYearlyLeave() {
        LeaveDisbursalDto leaveDisbursal = restTemplate.getForObject(companyServiceBaseUrl + "/leave-types/half-yearly",
                LeaveDisbursalDto.class);
        if (leaveDisbursal == null) {
            log.warn("No monthly leave type found. Skipping disbursal.");
            System.out.println("No monthly leave type found. Skipping disbursal.");
        }

        disburseLeave(DisbursalFrequency.HALF_YEARLY, 2, leaveDisbursal);
    }

    @Transactional
    public void disburseLeave(DisbursalFrequency frequency, int divisor, LeaveDisbursalDto leaveDisbursal) {

        List<Employee> employees = employeeRepository.findAll();

        double daysToDisburse = leaveDisbursal.getTotalDays() / (double) divisor;

        List<EmployeeLeaveBalance> employeeLeaveBalances = leaveBalanceRepository.findAll();
        Map<String, EmployeeLeaveBalance> balanceMap = employeeLeaveBalances.stream()
                .collect(Collectors.toMap(EmployeeLeaveBalance::getEmployeeId, b -> b));

        List<LeaveTransaction> leaveTransactions = new ArrayList<>();
        List<EmployeeLeaveBalance> updatedLeaveBalances = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeLeaveBalance balance = balanceMap.get(employee.getEmployeeId());

            if (balance == null) {
                balance = new EmployeeLeaveBalance();
                balance.setEmployeeId(employee.getEmployeeId());
                balance.setLeaveTypeName(frequency.toString());
                balance.setLeaveBalance(0);
            }

            balance.setLeaveBalance(balance.getLeaveBalance() + daysToDisburse);
            updatedLeaveBalances.add(balance);

            LeaveTransaction transaction = new LeaveTransaction();
            transaction.setEmployeeId(employee.getEmployeeId());
            transaction.setLeaveTypeName(frequency.toString());
            transaction.setTransactionType(LeaveTransactionType.CREDIT);
            transaction.setDays(daysToDisburse);
            leaveTransactions.add(transaction);
        }

        leaveBalanceRepository.saveAll(updatedLeaveBalances);
        leaveTransactionRepository.saveAll(leaveTransactions);
    }

}