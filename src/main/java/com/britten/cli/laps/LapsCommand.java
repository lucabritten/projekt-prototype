package com.britten.cli.laps;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(
        name = "laps",
        description = "Lap related commands",
        mixinStandardHelpOptions = true,
        subcommands = {
                LapsAllCommand.class,
                LapsFastestCommand.class,
                LapsCompareCommand.class
        }
)
public class LapsCommand{
}