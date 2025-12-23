package com.britten.cli;

import com.britten.domain.Lap;
import com.britten.domain.SessionType;
import com.britten.infrastructure.OpenF1Client;
import com.britten.util.LapsFormatter;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(
        name = "all",
        description = "Show laps for a driver in a session"
)
public class LapsAllCommand implements Runnable{

    @CommandLine.Option(names = "--year", required = true)
    int year;

    @CommandLine.Option(names = "--country", required = true)
    String country;

    @CommandLine.Option(names = "--driver", required = true)
    int driverNumber;

    private final OpenF1Client client;

    public LapsAllCommand(OpenF1Client client){
        this.client = client;
    }

    public LapsAllCommand(){
        this(new OpenF1Client());
    }

    @Override
    public void run() {
        List<Lap> laps = client.getLapsForSession(country, year, driverNumber, SessionType.RACE);
        System.out.println(LapsFormatter.format(laps));
    }
}
