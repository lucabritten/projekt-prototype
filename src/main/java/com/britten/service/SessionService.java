package com.britten.service;

import com.britten.domain.Session;
import com.britten.domain.SessionType;
import com.britten.infrastructure.OpenF1Client;
import com.britten.repository.SessionRepository;

public class SessionService {

    private final SessionRepository repository;
    private final OpenF1Client client;

    public SessionService(SessionRepository repository, OpenF1Client client){
        this.repository = repository;
        this.client = client;
    }

    public Session getOrCreate(int meetingKey, SessionType type){
        return repository.findByMeetingAndType(meetingKey, type)
                .orElseGet(() -> {
                    Session session = client.getSessionByMeetingKeyAndType(meetingKey, type);
                    repository.save(session, meetingKey);
                    return session;
                });
    }
}
