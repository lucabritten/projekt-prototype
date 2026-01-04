package com.britten.repository.jooq;

import com.britten.domain.Driver;
import com.britten.jooq.tables.Drivers;
import com.britten.repository.DriverRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Konkrete jooq implementierung des Interfaces
 */
@Repository
public class JooqDriverRepository implements DriverRepository {

    private final DSLContext dslContext;

    public JooqDriverRepository(DSLContext dslContext){
        this.dslContext = dslContext;
    }

    @Override
    public Optional<Driver> findByDriverNumber(int driverNumber) {
        return dslContext
                .select(Drivers.DRIVERS.DRIVER_NUMBER,
                        Drivers.DRIVERS.FULL_NAME
                )
                .from(Drivers.DRIVERS)
                .where(Drivers.DRIVERS.DRIVER_NUMBER.eq(driverNumber))
                .fetchOptional(record ->
                        new Driver(
                                record.get(Drivers.DRIVERS.FULL_NAME),
                                record.get(Drivers.DRIVERS.DRIVER_NUMBER)
                                )
                        );
    }

    @Override
    public void save(Driver driver){
        dslContext
                .insertInto(Drivers.DRIVERS)
                .values(driver.number(),
                        driver.name()
                )
                .execute();
    }
}
