package com.britten.repository.jooq;

import com.britten.domain.Session;
import com.britten.domain.SessionType;
import com.britten.repository.SessionRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.britten.jooq.tables.Sessions.*;
@Repository
public class JooqSessionRepository implements SessionRepository {

    private final DSLContext context;

    public JooqSessionRepository(DSLContext context){
        this.context = context;
    }
    @Override
    public Optional<Session> findByMeetingAndType(int meetingKey, SessionType type) {
        return context.select(SESSIONS.SESSION_TYPE, SESSIONS.SESSION_NAME, SESSIONS.SESSION_KEY)
                .from(SESSIONS)
                .where(SESSIONS.MEETING_KEY.eq(meetingKey).and(SESSIONS.SESSION_TYPE.eq(type.name())))
                .fetchOptional(record ->
                        new Session(
                                SessionType.valueOf(record.get(SESSIONS.SESSION_TYPE)),
                                record.get(SESSIONS.SESSION_NAME),
                                record.get(SESSIONS.SESSION_KEY)
                                )
                );
    }

    @Override
    public void save(Session session, int meetingKey){
        context.insertInto(SESSIONS)
                .values(session.sessionKey(),
                        meetingKey,
                        session.sessionType().toString(),
                        session.sessionName()
                )
                .execute();
    }
}
