package com.britten.cli.laps;

import com.britten.domain.Lap;
import com.britten.domain.SessionType;
import com.britten.infrastructure.OpenF1Client;
import com.britten.util.LapsFormatter;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.Comparator;
import java.util.List;
@Component
@CommandLine.Command(
        name = "fastest",
        description = "Show the fastest lap of a driver in a session",
        mixinStandardHelpOptions = true,
        header = "Fastest lap analysis",
        footer = {
                "",
                "Example:",
                "  f1-inside laps fastest --year 2021 --country Austria --driver 1"
        }
)
public class LapsFastestCommand implements Runnable{

    @CommandLine.Option(
            names = "--year",
            description = "Season year (e.g. 2021)",
            required = true
    )
    int year;

    @CommandLine.Option(
            names = "--country",
            description = "Grand Prix country (e.g. Austria)",
            required = true
    )
    String country;

    @CommandLine.Option(
            names = "--driver",
            description = "Driver number (e.g. 1 for Verstappen)",
            required = true
    )
    int driverNumber;

    private final OpenF1Client client;

    public LapsFastestCommand(OpenF1Client client){
        this.client = client;
    }

    public LapsFastestCommand(){
        this(new OpenF1Client());
    }

    @Override
    public void run() {
        List<Lap> laps = client.getLapsForSession(
                country,
                year,
                driverNumber,
                SessionType.Race
        );

        laps.stream()
                .min(Comparator.comparingDouble(Lap::duration))
                .ifPresentOrElse(
                        lap -> System.out.println(
                                LapsFormatter.formatFastestLap(lap)
                        ),
                        () -> System.out.println("No laps found")
                );
    }
}
