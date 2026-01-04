package com.britten.cli.laps;

import com.britten.infrastructure.OpenF1Client;
import com.britten.infrastructure.db.JooqConfig;
import com.britten.repository.jooq.JooqLapRepository;
import com.britten.repository.jooq.JooqMeetingRepository;
import com.britten.repository.jooq.JooqSessionRepository;
import com.britten.service.MeetingService;
import com.britten.service.SessionService;
import com.britten.service.laps.LapChartService;
import com.britten.service.laps.LapsService;
import org.jooq.DSLContext;
import picocli.CommandLine;

public class CommandFactory implements CommandLine.IFactory {

    private final DSLContext ctx = JooqConfig.createContext();
    private final OpenF1Client client = new OpenF1Client();

    private final MeetingService meetingService = new MeetingService(new JooqMeetingRepository(ctx), client);
    private final SessionService sessionService = new SessionService(new JooqSessionRepository(ctx), client);

    private final LapsService lapsService =
            new LapsService(
                    client,
                    meetingService,
                    sessionService,
                    new JooqLapRepository(ctx)
            );

    private final LapChartService lapChartService = new LapChartService();

    @Override
    public <K> K create(Class<K> cls) throws Exception {
        if (cls == LapsCompareCommand.class) {
            return cls.cast(
                    new LapsCompareCommand(lapChartService, lapsService)
            );
        }
        return cls.getDeclaredConstructor().newInstance();
    }
}
