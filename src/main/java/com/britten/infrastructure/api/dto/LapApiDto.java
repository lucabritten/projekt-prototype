package com.britten.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record LapApiDto(int driver_number, int session_key, int lap_number, float lap_duration) {
}
