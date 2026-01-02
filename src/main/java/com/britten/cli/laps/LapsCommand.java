package com.britten.cli.laps;

import picocli.CommandLine;

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