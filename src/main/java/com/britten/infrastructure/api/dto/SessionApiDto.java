package com.britten.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SessionApiDto(int session_key,
                            int meeting_key,
                            String session_type,
                            String session_name)
{ }
