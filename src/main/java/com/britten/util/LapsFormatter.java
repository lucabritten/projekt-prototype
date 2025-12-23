package com.britten.util;

import com.britten.domain.Lap;

import java.util.List;

public class LapsFormatter {


    public static String format(List<Lap> laps){
        StringBuilder sb = new StringBuilder();

        final String BOLD = "\u001B[1m";
        final String RESET = "\u001B[0m";

        sb.append(BOLD)
          .append(String.format(
                "%-5s | %-8s%n",
                "Lap",
                "Time"
                ))
          .append(RESET);

        sb.append("------+---------\n");

        for(Lap lap : laps) {
            sb.append(String.format(
                    "%-5d | %8.3f%n",
                    lap.lapNumber(),
                    lap.duration()
            ));
        }
        return sb.toString();
    }

    public static String formatFastestLap(Lap lap) {
        return String.format(
                """
                Fastest Lap
                ------------
                Lap:  %d
                Time: %.3f
                """,
                lap.lapNumber(),
                lap.duration()
        );
    }
}
