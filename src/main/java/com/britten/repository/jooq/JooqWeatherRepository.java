package com.britten.repository.jooq;

import com.britten.domain.SessionWeatherData;
import com.britten.repository.WeatherRepository;
import org.jooq.DSLContext;

import java.util.Optional;

import static com.britten.jooq.tables.Weather.*;
import static org.jooq.impl.DSL.asterisk;

public class JooqWeatherRepository implements WeatherRepository {

    private DSLContext context;

    public JooqWeatherRepository(DSLContext context){
        this.context = context;
    }

    @Override
    public Optional<SessionWeatherData> findBySessionKey(int sessionKey){
        return context
                .select(asterisk())
                .from(WEATHER)
                .where(WEATHER.SESSION_KEY.eq(sessionKey))
                .fetchOptional(record ->
                        new SessionWeatherData(
                                record.get(WEATHER.AVG_AIR_TEMP),
                                record.get(WEATHER.AVG_HUMIDITY),
                                record.get(WEATHER.MEETING_KEY),
                                record.get(WEATHER.AVG_PRESSURE),
                                record.get(WEATHER.IS_RAIN) == 1,
                                record.get(WEATHER.SESSION_KEY),
                                record.get(WEATHER.AVG_TRACK_TEMP),
                                record.get(WEATHER.AVG_WIND_DIRECTION),
                                record.get(WEATHER.AVG_WIND_SPEED)
                        )
                );
    }

    @Override
    public void save(SessionWeatherData data){
        context.insertInto(WEATHER)
                .values(data.avgAirTemp(),
                        data.avgHumidity(),
                        data.meetingKey(),
                        data.avgPressure(),
                        data.isRainfall() ? 1 : 0,
                        data.sessionKey(),
                        data.avgTrackTemp(),
                        data.avgWindDirection(),
                        data.avgWindSpeed()
                )
                .execute();

    }
}
