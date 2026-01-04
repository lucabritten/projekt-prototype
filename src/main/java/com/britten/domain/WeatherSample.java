package com.britten.domain;

public record WeatherSample(float airTemp,
                            float humidity,
                            float pressure,
                            boolean isRainfall,
                            float trackTemp,
                            float windDirection,
                            float windSpeed,
                            int sessionKey,
                            int meetingKey) {
}
