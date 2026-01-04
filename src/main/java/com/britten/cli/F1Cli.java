package com.britten.cli;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

/**
 * Obercommand: Später kann dann zb: f1-inside driver --number 44 auf der Konsole ausgeführt werden
 * Jeder Command brauch eine einzelne Klasse (Baumaufbau) f1-inside --> Driver/Laps/... --> zb. f1-inside laps fastest --.....
 */
@Component
@CommandLine.Command(
        name = "f1-inside",
        description = "Formula 1 data analysis CLI",
        mixinStandardHelpOptions = true,
        subcommands = {
                DriverCommand.class,
                LapsCommand.class
        }

)
public class F1Cli {

}
