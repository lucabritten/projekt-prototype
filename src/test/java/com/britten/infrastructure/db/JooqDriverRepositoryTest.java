package com.britten.infrastructure.db;

import com.britten.domain.Driver;
import com.britten.repository.jooq.JooqDriverRepository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class JooqDriverRepositoryTest {
    private DSLContext dslContext;
    private JooqDriverRepository repository;

    @BeforeEach
    void setUp(){
        dslContext = DSL.using(
                "jdbc:sqlite::memory:"
        );

        dslContext.execute("""
            CREATE TABLE Drivers (
                driver_number INTEGER PRIMARY KEY,
                full_name TEXT NOT NULL
            )
        """);

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
}
