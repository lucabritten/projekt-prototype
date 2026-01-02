package com.britten.repository;

import com.britten.domain.Session;
import com.britten.domain.SessionType;

import java.util.Optional;

public interface SessionRepository {
    Optional<Session> findByMeetingAndType(int meetingKey, SessionType type);
    void save(Session session, int meetingKey);
}
