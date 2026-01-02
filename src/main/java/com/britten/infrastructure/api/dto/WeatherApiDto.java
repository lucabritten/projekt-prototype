package com.britten.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherApiDto(float air_temperature,
                            int humidity,
                            int meeting_key,
                            float pressure,
                            int rainfall,
                            int session_key,
                            float track_temperature,
                            int wind_direction,
                            float wind_speed) {
}
