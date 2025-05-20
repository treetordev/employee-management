package com.hrms.employee.management.service;

import com.hrms.employee.management.dao.LeaveTracker;
import com.hrms.employee.management.dto.ActionItemExtRequest;
import com.hrms.employee.management.utility.ActionItemHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class ActionItemService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${utility_base_url}")
    private String utilityBaseUrl;


    public void createActionItem(String employeeId, LeaveTracker leaveTracker, String assignedManagerId) {
        if(assignedManagerId==null || assignedManagerId.isEmpty())
            return;
        String url = utilityBaseUrl + "/action-items";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ActionItemExtRequest request= ActionItemHelper.convertToLeaveRequest(leaveTracker, employeeId,assignedManagerId);
        log.info("ex request :{}",request);
        HttpEntity<ActionItemExtRequest> requestEntity = new HttpEntity<>(request, headers);
        restTemplate.postForEntity(
                url,
                requestEntity, Void.class
        );

    }



}
