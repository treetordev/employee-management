package com.hrms.employee.management.dto;

import java.util.List;

import com.hrms.employee.management.dao.Employee;

import lombok.Data;

@Data
public class MonthlyTimeSheetDto {

    private int year;
    private int month;
    private Employee employee;
    private SummaryDto summary;
    private List<TimesheetDto> timesheetList;
}
