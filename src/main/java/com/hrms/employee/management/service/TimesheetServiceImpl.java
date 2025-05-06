package com.hrms.employee.management.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrms.employee.management.dao.Employee;
import com.hrms.employee.management.dao.LeaveTracker;
import com.hrms.employee.management.dao.Timesheet;
import com.hrms.employee.management.dto.DailyTimesheetDto;
import com.hrms.employee.management.dto.EmployeeTimeSheetDto;
import com.hrms.employee.management.dto.LeaveTrackerDto;
import com.hrms.employee.management.dto.MonthlyTimeSheetDto;
import com.hrms.employee.management.dto.SummaryDto;
import com.hrms.employee.management.dto.TimesheetDto;
import com.hrms.employee.management.repository.EmployeeRepository;
import com.hrms.employee.management.repository.LeaveTrackerRepository;
import com.hrms.employee.management.repository.TimesheetRepository;

@Service
public class TimesheetServiceImpl implements TimesheetService {

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveTrackerRepository leaveTrackerRepository;

    @Override
    public TimesheetDto logWork(String employeeId, TimesheetDto timesheetDto) {
        // Fetch the employee
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Create Timesheet entry
        Timesheet timesheet = new Timesheet();
        timesheet.setEmployee(employee);
        timesheet.setWorkDate(timesheetDto.getWorkDate());
        timesheet.setClockIn(timesheetDto.getClockIn());
        timesheet.setClockOut(timesheetDto.getClockOut());

        // Calculate total hours
        Duration duration = Duration.between(timesheetDto.getClockIn(), timesheetDto.getClockOut());
        timesheet.setTotalHours(duration.toHours() + (duration.toMinutesPart() / 60.0));

        // Save the entity
        Timesheet savedTimesheet = timesheetRepository.save(timesheet);

        // Convert and return DTO
        return convertToDto(savedTimesheet);
    }

    @Override
    public List<TimesheetDto> getTimesheetByEmployeeId(String employeeId) {
        List<Timesheet> timesheets = timesheetRepository.findByEmployee_EmployeeId(employeeId);
        return timesheets.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private TimesheetDto convertToDto(Timesheet timesheet) {
        TimesheetDto dto = new TimesheetDto();
        dto.setTimesheetId(timesheet.getId());
        dto.setEmployeeId(timesheet.getEmployee().getEmployeeId());
        dto.setWorkDate(timesheet.getWorkDate());
        dto.setClockIn(timesheet.getClockIn());
        dto.setClockOut(timesheet.getClockOut());
        dto.setTotalHours(timesheet.getTotalHours());
        return dto;
    }

    @Override
    public TimesheetDto clock(String employeeId, TimesheetDto timesheetDto) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Timesheet timesheet = timesheetRepository.findByworkDateAndEmployee_EmployeeId(
                timesheetDto.getWorkDate(), employeeId);


        if (timesheet == null) {
            timesheet= new Timesheet();
            timesheet.setEmployee(employee);
            timesheet.setWorkDate(timesheetDto.getWorkDate());
            timesheet.setClockIn(timesheetDto.getClockIn());
        }
        else{
            if (timesheet.getClockIn() == null) {
                throw new RuntimeException("No clock-in record found for this date.");
            }
            if(timesheet.getClockOut() != null) {
                throw new RuntimeException("Already clocked out for this date.");
            }
            timesheet.setClockOut(timesheetDto.getClockOut());
            Duration duration = Duration.between(timesheet.getClockIn(), timesheetDto.getClockOut());
            timesheet.setTotalHours(duration.toHours() + (duration.toMinutesPart() / 60.0));

        }

        Timesheet savedTimesheet = timesheetRepository.save(timesheet);

        return convertToDto(savedTimesheet);
    }

    @Override
    public List<TimesheetDto> getMonthlyTimesheet(String employeeId, int month, int year) {
        List<Timesheet> timesheets = timesheetRepository.findByEmployeeAndMonth(employeeId, month, year);
        if (timesheets.isEmpty()) {
            throw new RuntimeException("No timesheet found for the given month.");
        }
        // LeaveTracker leaveTracker =
        // leaveTrackerRepository.findLeaveByDateAndEmployeeId(date, employeeId);

        YearMonth yearMonth = YearMonth.of(year, month);
        int noOfDaysInMonth = yearMonth.lengthOfMonth();
        int timesheetCount = timesheets.size();

        MonthlyTimeSheetDto monthlyTimeSheetDto = new MonthlyTimeSheetDto();

        SummaryDto summaryDto = new SummaryDto();
        summaryDto.setTotalDays(noOfDaysInMonth);
        summaryDto.setFilledDays(timesheetCount);
        summaryDto.setRemainingDays(noOfDaysInMonth - timesheetCount);
        summaryDto.setPendingApprovalDays(0);
        summaryDto.setRemainingDays(0);
        summaryDto.setLeaveDays(0);

        monthlyTimeSheetDto.setYear(timesheets.get(0).getWorkDate().getYear());
        monthlyTimeSheetDto.setMonth(month);
        monthlyTimeSheetDto.setEmployee(timesheets.get(0).getEmployee());

        return timesheets.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DailyTimesheetDto getDailyTimesheet(String employeeId, LocalDate date) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        EmployeeTimeSheetDto employeeTimeSheetDto = new EmployeeTimeSheetDto();
        employeeTimeSheetDto.setEmployeeId(employee.getEmployeeId());
        employeeTimeSheetDto.setEmployeeName(employee.getName());
        employeeTimeSheetDto.setEmployeeDepartment(employee.getJobTitle());
        employeeTimeSheetDto.setEmployeeDesignation(employee.getRole());

        String dayOfWeek = date.getDayOfWeek().name();
        LocalDate today = LocalDate.now();
        Timesheet timesheet = timesheetRepository.findByEmployeeAndDate(employeeId, date);
        LeaveTracker leaveTracker = leaveTrackerRepository.findLeaveByDateAndEmployeeId(date, employeeId);

        // Initial builder setup
        DailyTimesheetDto.DailyTimesheetDtoBuilder builder = DailyTimesheetDto.builder()
                .employee(employeeTimeSheetDto)
                .date(date)
                .dayOfWeek(dayOfWeek)
                .editable(false)
                .leaveDay(false)
                .weekend(false)
                .holiday(false)
                .futureDate(false);

        if (leaveTracker != null && (date.isBefore(today) || date.isEqual(today))) {
            return builder
                    .timesheetStatus("Leave")
                    .leaveTracker(convertToLeaveTrackerDto(leaveTracker))
                    .build();
        }

        if (date.isAfter(today) && leaveTracker != null) {
            return builder
                    .timesheetStatus("Future Date")
                    .futureDate(true)
                    .build();
        }

        if (dayOfWeek.equalsIgnoreCase("SATURDAY") || dayOfWeek.equalsIgnoreCase("SUNDAY")) {
            return builder
                    .timesheetStatus("weekend")
                    .weekend(true)
                    .build();
        }

        if (date.isAfter(today)) {
            return builder
                    .message("Employee data not found.")
                    .build();
        }

        if (timesheet != null) {
            return builder
                    .timesheetStatus("filled")
                    .timesheet(convertToDto(timesheet))
                    .build();
        } else {
            return builder
                    .timesheetStatus("unfilled")
                    .editable(true)
                    .message("Please complete your timesheet for this day.")
                    .build();
        }
    }

    private LeaveTrackerDto convertToLeaveTrackerDto(LeaveTracker leaveTracker) {
        LeaveTrackerDto leaveTrackerDto = new LeaveTrackerDto();
        leaveTrackerDto.setLeaveId(leaveTracker.getId());
        leaveTrackerDto.setStartDate(leaveTracker.getStartDate());
        leaveTrackerDto.setEndDate(leaveTracker.getEndDate());
        leaveTrackerDto.setLeaveType(leaveTracker.getLeaveType());
        // leaveTrackerDto.setStatus(leaveTracker.getStatus());
        leaveTrackerDto.setReason(leaveTracker.getReason());
        // leaveTrackerDto.setApprovedBy(LeaveTracker.getLeaveStatus());
        // LeaveTrackerDto.setApprovedOn(leaveTracker.getApprovedOn());
        return leaveTrackerDto;

    }

}