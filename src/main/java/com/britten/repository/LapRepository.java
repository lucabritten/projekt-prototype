package com.britten.repository;

import com.britten.domain.Lap;

import java.util.List;
import java.util.Optional;

public interface LapRepository {

    void saveAll(List<Lap> laps);
    List<Lap> findBySessionAndDriver(int sessionKey, int driverNumber);
    boolean existsForSession(int sessionKey);

}
