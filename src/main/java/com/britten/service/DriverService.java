package com.britten.service;

import com.britten.domain.Driver;
import com.britten.infrastructure.OpenF1Client;
import com.britten.repository.DriverRepository;

import java.util.Optional;

/**
 * Konkrete DriverService Klasse implementiert Abstract Service
 *
 */
public class DriverService extends AbstractService<Driver, Integer>{

    private final DriverRepository repository;
    private final OpenF1Client openF1Client;

    public DriverService(DriverRepository repository, OpenF1Client openF1Client){
        this.repository = repository;
        this.openF1Client = openF1Client;
    }

    @Override
    protected Optional<Driver> findInDb(Integer driverNumber) {
        return repository.findByDriverNumber(driverNumber);
    }

    @Override
    protected Driver fetchFromApi(Integer driverNumber) {
        return openF1Client
                .getDriverByNumber(driverNumber);
    }

    @Override
    protected void save(Driver driver) {
        repository.save(driver);
    }
}
