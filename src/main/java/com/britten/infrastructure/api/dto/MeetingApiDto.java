package com.britten.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MeetingApiDto(int meeting_key,
                            String country_name,
                            int year,
                            String meeting_name,
                            String date_start) {
}
