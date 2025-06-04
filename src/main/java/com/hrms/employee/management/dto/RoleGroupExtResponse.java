package com.hrms.employee.management.dto;

import lombok.Data;

@Data
public class RoleGroupExtResponse {
    private Long id;
    private String name;
    private String description;
    private String kcGroupIdRef;
}