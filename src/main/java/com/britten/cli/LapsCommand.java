package com.britten.cli;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(
        name = "laps",
        description = "Lap related commands",
        mixinStandardHelpOptions = true,
        subcommands = {
                LapsAllCommand.class,
                LapsFastestCommand.class
        }
)
public class LapsCommand{
}