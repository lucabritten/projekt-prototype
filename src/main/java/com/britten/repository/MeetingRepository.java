package com.britten.repository;

import com.britten.domain.Meeting;

import javax.swing.text.html.Option;
import java.util.Optional;


public interface MeetingRepository {

    Optional<Meeting> findByCountryAndYear(String country, int year);
    void save(Meeting meeting);
}
