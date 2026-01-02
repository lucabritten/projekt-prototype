package com.britten;

import com.britten.cli.F1Cli;
import com.britten.cli.laps.CommandFactory;
import com.britten.infrastructure.OpenF1Client;
import com.britten.infrastructure.db.JooqConfig;
import com.britten.repository.jooq.JooqWeatherRepository;
import com.britten.service.WeatherDataService;
import picocli.CommandLine;

import java.util.List;

public class App 
{
    public static void main( String[] args ) throws Exception{
//        CommandLine cmd = new CommandLine(new F1Cli(), new CommandFactory());
//        cmd.execute(args);

        WeatherDataService service = new WeatherDataService(new JooqWeatherRepository(JooqConfig.createContext()), new OpenF1Client());
        System.out.println(service.getOrCreateForSession(9920));
    }
}
