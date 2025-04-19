package com.hrms.employee.management.service;

import java.util.List;
import java.util.Map;

import com.hrms.employee.management.dto.GenerateTokenRequest;
import com.hrms.employee.management.dto.OnboardKeycloakUserRequest;
import com.hrms.employee.management.utility.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import com.hrms.employee.management.dao.Employee;
import com.hrms.employee.management.dto.EmployeeCountDto;
import com.hrms.employee.management.dto.EmployeeDto;
import com.hrms.employee.management.repository.EmployeeRepository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    @Value("${iam_service_base_url}")
    private String iamServiceBaseUrl;

    @Value("${tenant_management_base_url}")
    private String tenantUrl;

    @Autowired
    private RestTemplate restTemplate;

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Employee createEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(String employeeId, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeMapper.updateEntity(employee, employeeDto);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(String employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public EmployeeCountDto getEmployeeCounts() {
        long totalEmployees = employeeRepository.count();
        long activeEmployees = employeeRepository.countByJobStatus("Active");
        return new EmployeeCountDto(totalEmployees, activeEmployees);
    }

    @Override
    public String onboardUserInKeycloak(EmployeeDto employeeDto, String currentTenant) {

        Map<String,Object> masterRealmDetails =getMasterRealmDetails();
        String token=getKeycloakToken(masterRealmDetails);
        log.info("token:{}",token);
        String url = iamServiceBaseUrl + Constants.ONBOARD_KEYCLOAK_USER;
        OnboardKeycloakUserRequest userRequest = EmployeeOnboardingHelper.getOnboardKeycloakUserRequest(employeeDto,TenantContext.getCurrentTenant());
        HttpEntity<OnboardKeycloakUserRequest> entity = new HttpEntity<>(userRequest, createHeaders(token));

        ResponseEntity<Map<String, String>> response = makeApiCall(url, HttpMethod.POST, entity, new ParameterizedTypeReference<>() {
                },
                ErrorCodes.KEYCLOAK_USER_ONBOARD_ERROR, "Failed to onboard Keycloak user");

        Map<String, String> responseBody = response.getBody();
        if (responseBody == null || !responseBody.containsKey("userId")) {
            throw new KeycloakException(ErrorCodes.KEYCLOAK_USER_ONBOARD_ERROR, "Missing user ID in response");
        }
        return responseBody.get("userId");
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private <T> ResponseEntity<T> makeApiCall(String url, HttpMethod method, HttpEntity<?> entity, ParameterizedTypeReference<T> responseType, int errorCode, String errorMessage) {
        try {
            return restTemplate.exchange(url, method, entity, responseType);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("{} - Status: {}, Response: {}", errorMessage, e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new RuntimeException(errorMessage + " - Status: " + e.getStatusCode(), e);
        }
    }

    public String getKeycloakToken(Map<String,Object> masterRealmDetails) {
        String adminAccessTokenUrl = iamServiceBaseUrl + Constants.GET_ADMIN_TOKEN;
        GenerateTokenRequest tokenRequest = EmployeeMapper.getGenerateTokenRequest(masterRealmDetails);
        HttpEntity<GenerateTokenRequest> entity = new HttpEntity<>(tokenRequest, createHeaders(null));

        ResponseEntity<Map<String, String>> response = makeApiCall(adminAccessTokenUrl, HttpMethod.POST, entity,
                new ParameterizedTypeReference<>() {}, 500, "Failed to obtain admin access token");
        log.info("fetched the admin token succesfully");

        return response.getBody().get("token");
    }


    private Map<String, Object> getMasterRealmDetails() {
        String url = tenantUrl +"/api/v1/tenants/auth-config/" + "master";
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> responseBody = response.getBody();
        return (Map<String, Object>) responseBody.get("data");
    }

}