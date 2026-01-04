package com.britten.service.laps;

import com.britten.domain.Lap;
import com.britten.domain.Meeting;
import com.britten.domain.Session;
import com.britten.domain.SessionType;
import com.britten.infrastructure.OpenF1Client;
import com.britten.repository.LapRepository;
import com.britten.service.MeetingService;
import com.britten.service.SessionService;

import java.util.List;

public class LapsService{

    private final OpenF1Client client;
    private final MeetingService meetingService;
    private final SessionService sessionService;
    private final LapRepository lapRepository;

    public LapsService(OpenF1Client client, MeetingService meetingService, SessionService sessionService, LapRepository lapsRepository){
        this.client = client;
        this.meetingService = meetingService;
        this.sessionService =sessionService;
        this.lapRepository = lapsRepository;
    }

    public List<Lap> getLapsForSession(
            String country,
            int year,
            SessionType type,
            int driverNumber
    ){
        Meeting meeting = meetingService.getOrCreate(country, year);
        Session session = sessionService.getOrCreate(meeting.meetingKey(), type);

        if(!lapRepository.existsForSession(session.sessionKey())){
            List<Lap> laps = client.fetchLaps(session.sessionKey());
            lapRepository.saveAll(laps);
        }
        return lapRepository.findBySessionAndDriver(session.sessionKey(), driverNumber);
    }

    private List<Lap> fetchLaps(String country, int year, int driverNumber, SessionType type){
        return client.getLapsForSession(country, year,driverNumber, type);
    }

}
