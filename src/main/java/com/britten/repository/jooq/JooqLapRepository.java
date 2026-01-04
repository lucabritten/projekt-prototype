package com.britten.repository.jooq;

import com.britten.domain.Lap;
import com.britten.repository.LapRepository;
import org.jooq.DSLContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.britten.repository.LapRepository;

import static com.britten.jooq.tables.Laps.LAPS;

public class JooqLapRepository implements LapRepository {

    private final DSLContext context;

    public JooqLapRepository(DSLContext context){
        this.context = context;
    }

    @Override
    public void saveAll(List<Lap> laps) {
        laps.forEach(lap ->
                context.insertInto(LAPS)
                        .values(lap.driverNumber(),
                                lap.sessionKey(),
                                lap.lapNumber(),
                                lap.duration()
                        )
                        .execute()
        );
    }

    @Override
    public List<Lap> findBySessionAndDriver(int sessionKey, int driverNumber) {
        return context
                .select(
                        LAPS.DRIVER_NUMBER,
                        LAPS.LAP_DURATION,
                        LAPS.LAP_NUMBER,
                        LAPS.SESSION_KEY
                )
                .from(LAPS)
                .where(
                        LAPS.SESSION_KEY.eq(sessionKey)
                                .and(LAPS.DRIVER_NUMBER.eq(driverNumber))
                )
                .fetch(record -> new Lap(
                        record.get(LAPS.DRIVER_NUMBER),
                        record.get(LAPS.LAP_DURATION),
                        record.get(LAPS.LAP_NUMBER),
                        record.get(LAPS.SESSION_KEY)
                ));
    }

    @Override
    public boolean existsForSession(int sessionKey){
        return context.select(LAPS.SESSION_KEY)
                .from(LAPS)
                .where(LAPS.SESSION_KEY.eq(sessionKey))
                .fetch()
                .isNotEmpty();
    }
}
