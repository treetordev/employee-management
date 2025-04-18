package com.hrms.employee.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeolocationResponse {
    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

    // Add these properties to match potential API responses
    @JsonProperty("ip")
    private String ipAddress;

    @JsonProperty("region")
    private String region;
}
