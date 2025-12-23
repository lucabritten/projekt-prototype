package com.britten.cli;

import picocli.CommandLine;

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