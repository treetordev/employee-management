package com.hrms.employee.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ActionItemExtRequest {

    private String title;

    private String description;

    private ActionType type;

    private String initiatorUserId;

    private String assigneeUserId;

    private Long referenceId;

    public enum ActionType {
        TIMESHEET, LEAVE, WFH, EXPENSE, ASSET_REQUEST
    }
}
