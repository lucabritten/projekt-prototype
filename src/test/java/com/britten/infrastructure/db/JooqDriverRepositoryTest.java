package com.britten.infrastructure.db;

import com.britten.domain.Driver;
import com.britten.repository.jooq.JooqDriverRepository;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.jooq.impl.SQLDataType.*;

public class JooqDriverRepositoryTest {
    private DSLContext dslContext;
    private JooqDriverRepository repository;

    @BeforeEach
    void setUp(){
        dslContext = DSL.using("jdbc:sqlite::memory:");

        dslContext.createTable("Drivers")
                .column("driver_number", INTEGER)
                .column("full_name", VARCHAR(30))
                .primaryKey("driver_number")
                .execute();

        repository = new JooqDriverRepository(dslContext);
    }

    @Test
    void shouldSaveAndFindDriver() {

        Driver driver = new Driver("Lewis Hamilton", 44);

        repository.save(driver);

        Optional<Driver> result =
                repository.findByDriverNumber(44);

        assertThat(result).isPresent();
        assertThat(result.get().name()).isEqualTo("Lewis Hamilton");
        assertThat(result.get().number()).isEqualTo(44);
    }

    @Test
    void shouldReturnEmptyOptionalIfDriverDoesNotExists(){
        final int NON_EXISTENT_DRIVER_NUMBER = 99;

        Optional<Driver> result = repository.findByDriverNumber(NON_EXISTENT_DRIVER_NUMBER);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldSaveMultipleDrivers(){
        repository.save(new Driver("Lewis Hamilton", 44));
        repository.save(new Driver("Lando Norris", 4));

        assertThat(repository.findByDriverNumber(44)).isPresent();
        assertThat(repository.findByDriverNumber(4)).isPresent();
    }

    @Test
    void shouldFailWhenSavingDriverWithSameNumberTwice(){
        repository.save(new Driver("Lewis Hamilton", 44));

        assertThatThrownBy(() -> repository.save(new Driver("Lewis Hamilton", 44)))
                .isInstanceOf(Exception.class);
    }
}
