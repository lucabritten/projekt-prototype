package com.britten.cli.laps;

import com.britten.domain.Lap;
import com.britten.domain.SessionType;
import com.britten.service.laps.LapChartService;
import com.britten.service.laps.LapsService;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
@CommandLine.Command(name = "compare")
public class LapsCompareCommand implements Runnable{

    private final LapsService lapsService;
    private final LapChartService lapChartService;

    public LapsCompareCommand(LapChartService lapChartService, LapsService lapsService){
        this.lapsService = lapsService;
        this.lapChartService = lapChartService;
    }

    @CommandLine.Option(names = "--country", required = true)
    String country;

    @CommandLine.Option(names = "--session", required = true)
    SessionType type;

    @CommandLine.Option(names = "--year", required = true)
    int year;

    @CommandLine.Option(names = "--drivers", split = ",", required = true)
    int[] drivers;

    @Override
    public void run() {
        Map<Integer, List<Lap>> data = new HashMap<>();

        for(int driver : drivers){
            data.put(driver,
                    lapsService.getLapsForSession(country, year, type, driver)
            );
        }

        lapChartService.generateCompareChart(data, "Laps comparison", "laps_compare.png");
    }
}
