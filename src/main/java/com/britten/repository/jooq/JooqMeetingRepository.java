package com.britten.repository.jooq;

import com.britten.domain.Meeting;
import com.britten.repository.MeetingRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.britten.jooq.tables.Meetings.MEETINGS;
@Repository
public class JooqMeetingRepository implements MeetingRepository {

    private final DSLContext context;

    public JooqMeetingRepository(DSLContext context){
        this.context = context;
    }

    @Override
    public Optional<Meeting> findByCountryAndYear(String country, int year) {
        return context.
                select(MEETINGS.COUNTRY_NAME, MEETINGS.YEAR, MEETINGS.MEETING_NAME, MEETINGS.MEETING_KEY)
                .from(MEETINGS)
                .where(MEETINGS.COUNTRY_NAME.eq(country).and(MEETINGS.YEAR.eq(year)))
                .fetchOptional(record ->
                   new Meeting(
                           record.get(MEETINGS.COUNTRY_NAME),
                           record.get(MEETINGS.YEAR),
                           record.get(MEETINGS.MEETING_NAME),
                           record.get(MEETINGS.MEETING_KEY)
                   )
                );
    }

    @Override
    public void save(Meeting meeting){
        context.insertInto(MEETINGS)
                .values(meeting.meetingKey(),
                        meeting.country(),
                        meeting.year(),
                        meeting.meetingName(),
                        "to be implemented"
                )
                .execute();
    }
}
