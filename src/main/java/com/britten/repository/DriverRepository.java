package com.britten.repository;

import com.britten.domain.Driver;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface, damit wir flexibel in der konkreten Technologie sind,
 * z.B jooq oder hibernate, kann um die Methoden erweitert werden,
 * die ein Driver Repo garantieren muss
 */
@Repository
public interface DriverRepository {

    Optional<Driver> findByDriverNumber(int driverNumber);

    void save(Driver driver);
}
