package com.britten.domain;

public record SessionWeatherData(double avgAirTemp,
                                 double avgHumidity,
                                 int meetingKey,
                                 double avgPressure,
                                 boolean isRainfall,
                                 int sessionKey,
                                 double avgTrackTemp,
                                 double avgWindDirection,
                                 double avgWindSpeed) {
}
