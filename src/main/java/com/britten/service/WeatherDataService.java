package com.britten.service;

import com.britten.domain.SessionWeatherData;
import com.britten.domain.WeatherSample;
import com.britten.infrastructure.OpenF1Client;
import com.britten.repository.WeatherRepository;

import java.util.List;

public class WeatherDataService {

    private final WeatherRepository weatherRepository;
    private final OpenF1Client client;

    public WeatherDataService(WeatherRepository repository, OpenF1Client client){
        this.weatherRepository = repository;
        this.client = client;
    }

    public SessionWeatherData getOrCreateForSession(int sessionKey){
        return weatherRepository.findBySessionKey(sessionKey)
                .orElseGet(() -> {
                    List<WeatherSample> samples =
                            client.fetchWeatherData(sessionKey);

                    double avgAirTemp = samples.stream()
                            .mapToDouble(WeatherSample::airTemp)
                            .average()
                            .orElse(Double.NaN);

                    double avgHumidity = samples.stream()
                            .mapToDouble(WeatherSample::humidity)
                            .average()
                            .orElse(Double.NaN);

                    double avgPressure = samples.stream()
                            .mapToDouble(WeatherSample::pressure)
                            .average()
                            .orElse(Double.NaN);

                    double avgTrackTemp = samples.stream()
                            .mapToDouble(WeatherSample::trackTemp)
                            .average()
                            .orElse(Double.NaN);

                    double avgWindSpeed = samples.stream()
                            .mapToDouble(WeatherSample::windSpeed)
                            .average()
                            .orElse(Double.NaN);

                    double avgWindDirection = samples.stream()
                            .mapToDouble(WeatherSample::windDirection)
                            .average()
                            .orElse(Double.NaN);

                    boolean isRainfall = samples.stream()
                            .map(WeatherSample::isRainfall)
                            .toList()
                            .contains(true);

                    int meetingKey = samples.get(0).meetingKey();

                    SessionWeatherData weatherData = new  SessionWeatherData(
                            avgAirTemp,
                            avgHumidity,
                            meetingKey,
                            avgPressure,
                            isRainfall,
                            sessionKey,
                            avgTrackTemp,
                            avgWindDirection,
                            avgWindSpeed
                    );

                    weatherRepository.save(weatherData);

                    return weatherData;
                });
    }
}
