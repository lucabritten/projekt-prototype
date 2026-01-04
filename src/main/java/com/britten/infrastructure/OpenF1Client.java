package com.britten.infrastructure;

import com.britten.domain.*;
import com.britten.infrastructure.api.dto.*;
import com.britten.util.Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Zuständigkeit: fetchen von Daten der openf1 api,
 * die jsons die wir von der api bekommen werden mit jackson in die dto's umgewandelt
 * und dann in unsere Entity-Typen gemappt, kann man alles noch cleaner implementieren, aber
 * für die Demo vom prototypen sollte das passen
 * Ganz unten hab ich zwei generische methoden implementiert, mit denen man eigentlich fast alle api requests umsetzen kann
 * Einmal zum fetchen von einem "einzelnen" objekt und einmal für eine Liste
 */
@Component
public class OpenF1Client {

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;
    private final String BASE_URL;

    public OpenF1Client(String url){
        this.okHttpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
        this.BASE_URL = url;
    }

    public OpenF1Client(){
        this("https://api.openf1.org/v1");
    }

    public Driver getDriverByNumber(int driverNumber){
        String url = BASE_URL + "/drivers?driver_number=" + driverNumber;

        DriverApiDto dto = fetchSingle(url, DriverApiDto[].class);
        return Mapper.toDriver(dto);
    }

    public List<Session> getSessionsByNameAndYear(String countryName, int year){
        MeetingApiDto meetingResponse = getMeetingDtoByNameAndYear(countryName, year);

        String url = BASE_URL + "/sessions?meeting_key=" + meetingResponse.meeting_key();

        List<SessionApiDto> dtos = fetchList(url, SessionApiDto[].class);
        return dtos.stream()
                .map(Mapper::toSession)
                .toList();
    }

    public Session getSessionByMeetingKeyAndType(int meetingKey, SessionType type){
        String url = BASE_URL + "/sessions?meeting_key=" + meetingKey;

        List<SessionApiDto> dtos = fetchList(url, SessionApiDto[].class);

        return dtos.stream()
                .filter(s -> s.session_type().contains(type.name()))
                .findFirst()
                .map(Mapper::toSession)
                .orElse(null);
    }

    public List<Lap> getLapsForSession(String countryName, int year, int driverNumber, SessionType type){
        List<Session> sessions = getSessionsByNameAndYear(countryName,year);

        int sessionKey = sessions.stream()
                .filter(session -> session.sessionType().equals(type))
                .map(Session::sessionKey)
                .findFirst()
                .orElse(-1);

        if (sessionKey < 0) throw new RuntimeException("Session not found");
        String url = BASE_URL + "/laps?session_key=" + sessionKey + "&driver_number=" + driverNumber;

        List<LapApiDto> laps = fetchList(url, LapApiDto[].class);

        return laps.stream()
                .map(Mapper::toLap)
                .toList();
    }


    private MeetingApiDto getMeetingDtoByNameAndYear(String countryName, int year){
        String url = BASE_URL + "/meetings?year=" + year + "&country_name=" + countryName;

        return fetchSingle(url, MeetingApiDto[].class);
    }

    public Meeting getMeetingByNameAndYear(String countryName, int year){
        return Mapper.toMeeting(getMeetingDtoByNameAndYear(countryName, year));
    }

    public List<Lap> fetchLaps(int sessionKey){
        String url = BASE_URL + "/laps?session_key=" + sessionKey;
        return fetchList(url, LapApiDto[].class).stream()
                .map(Mapper::toLap)
                .toList();
    }

    private <T> List<T> fetchList(String url, Class<T[]> arrayType){

        Request request = new Request.Builder()
                .url(url)
                .build();

        try(Response response = okHttpClient.newCall(request).execute()){
            if(!response.isSuccessful())
                throw new RuntimeException("HTTP error: " + response.code());

            String json = response.body().string();

            T[] result = objectMapper.readValue(json, arrayType);

            return Arrays.asList(result);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private <T> T fetchSingle(String url, Class<T[]> arrayType){
        List<T> result = fetchList(url, arrayType);
        if(result.isEmpty())
            throw new RuntimeException("No result.");

        return result.get(0);
    }

    public List<WeatherSample> fetchWeatherData(int sessionKey){
        String url = BASE_URL + "/weather?session_key=" + sessionKey;
        return fetchList(url, WeatherApiDto[].class).stream()
                .map(Mapper::toWeatherSample)
                .toList();
    }

}
