package com.britten.infrastructure;

import com.britten.infrastructure.db.JooqConfig;
import com.britten.repository.DriverRepository;
import com.britten.repository.jooq.JooqDriverRepository;
import com.britten.service.DriverService;

/**
 * Einfach nur damit die Main-Klasse bzw startpunkt(App.java) cleaner aussieht
 */
public class ApplicationContext {

    public static DriverService driverService(){
        return new DriverService(driverRepository(), openF1Client());
    }

    private static DriverRepository driverRepository(){
        return new JooqDriverRepository(JooqConfig.createContext());
    }

    private static OpenF1Client openF1Client(){
        return new OpenF1Client();
    }
}
