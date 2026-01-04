package com.britten.repository;

import com.britten.domain.SessionWeatherData;

import java.util.Optional;

public interface WeatherRepository {

    Optional<SessionWeatherData> findBySessionKey(int sessionKey);
    void save(SessionWeatherData data);
}
