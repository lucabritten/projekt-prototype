package com.britten.service;

import com.britten.domain.Meeting;
import com.britten.infrastructure.OpenF1Client;
import com.britten.repository.MeetingRepository;

public class MeetingService {

    private final MeetingRepository repository;
    private final OpenF1Client client;

    public MeetingService(MeetingRepository repository, OpenF1Client client){
        this.repository = repository;
        this.client = client;
    }

    public Meeting getOrCreate(String country, int year){
        return repository.findByCountryAndYear(country,year)
                .orElseGet(() -> {
                    Meeting m = client.getMeetingByNameAndYear(country, year);
                    repository.save(m);
                    return m;
                });
    }
}
