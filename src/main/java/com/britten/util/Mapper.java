package com.britten.util;

import com.britten.domain.Driver;
import com.britten.domain.Lap;
import com.britten.domain.Session;
import com.britten.domain.SessionType;
import com.britten.infrastructure.api.dto.DriverApiDto;
import com.britten.infrastructure.api.dto.LapApiDto;
import com.britten.infrastructure.api.dto.SessionApiDto;

/**
 * Mapping von api Datentyp zu Entity Datentyp
 */
public class Mapper {
    public static Driver toDriver(DriverApiDto driverDao){
        return new Driver(driverDao.full_name(), driverDao.driver_number());
    }

    public static Session toSession(SessionApiDto sessionDao){

        if(sessionDao.session_name().contains("Practice"))
            return new Session(SessionType.PRACTICE, sessionDao.session_name(), sessionDao.session_key());

        if(sessionDao.session_name().contains("Qualifying"))
            return new Session(SessionType.QUALIFYING, sessionDao.session_name(), sessionDao.session_key());

        if(sessionDao.session_name().contains("Sprint"))
            return new Session(SessionType.SPRINT, sessionDao.session_name(), sessionDao.session_key());

        if (sessionDao.session_name().contains("Race"))
            return new Session(SessionType.RACE, sessionDao.session_name(), sessionDao.session_key());

        return new Session(null, sessionDao.session_name(), sessionDao.session_key());
    }

    public static Lap toLap(LapApiDto lapApiDto){
        return new Lap(
                lapApiDto.driver_number(),
                lapApiDto.lap_duration(),
                lapApiDto.lap_number()
                );
    }
}
