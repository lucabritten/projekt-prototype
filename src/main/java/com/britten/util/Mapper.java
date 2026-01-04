package com.britten.util;

import com.britten.domain.*;
import com.britten.infrastructure.api.dto.*;

/**
 * Mapping von api Datentyp zu Entity Datentyp
 */
public class Mapper {
    public static Driver toDriver(DriverApiDto driverDao){
        return new Driver(driverDao.full_name(), driverDao.driver_number());
    }

    public static Session toSession(SessionApiDto sessionDao){

        if(sessionDao.session_name().contains("Practice"))
            return new Session(SessionType.Practice, sessionDao.session_name(), sessionDao.session_key());

        if(sessionDao.session_name().contains("Qualifying"))
            return new Session(SessionType.QUALIFYING, sessionDao.session_name(), sessionDao.session_key());

        if(sessionDao.session_name().contains("Sprint"))
            return new Session(SessionType.SPRINT, sessionDao.session_name(), sessionDao.session_key());

        if (sessionDao.session_name().contains("Race"))
            return new Session(SessionType.Race, sessionDao.session_name(), sessionDao.session_key());

        return new Session(null, sessionDao.session_name(), sessionDao.session_key());
    }

    public static Lap toLap(LapApiDto lapApiDto){
        return new Lap(
                lapApiDto.driver_number(),
                lapApiDto.lap_duration(),
                lapApiDto.lap_number(),
                lapApiDto.session_key()
                );
    }

    public static Meeting toMeeting(MeetingApiDto dto){
        return new Meeting(
                dto.country_name(),
                dto.year(),
                dto.meeting_name(),
                dto.meeting_key()
        );
    }

    public static WeatherSample toWeatherSample(WeatherApiDto dto){
        return new WeatherSample(dto.air_temperature(),
                dto.humidity(),
                dto.pressure(),
                dto.rainfall() == 1,
                dto.track_temperature(),
                dto.wind_direction(),
                dto.wind_speed(),
                dto.session_key(),
                dto.meeting_key()
        );
    }
}
