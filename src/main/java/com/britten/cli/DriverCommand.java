package com.britten.cli;

import com.britten.service.DriverService;
import picocli.CommandLine;
import org.springframework.stereotype.Component;

/**
 * Jeder cmd muss einfach das Runnable Interface implementieren
 * Mit der @Command Annotation kann der Command definiert werden
 * mit @Option oder @Parameter können Optionen definiert werden
 */

@Component
@CommandLine.Command(
        name = "driver",
        description = "Show driver information"
)

public class DriverCommand implements Runnable{

    @CommandLine.Option(
            names = "--number",
            description = "Number of the driver",
            required = true)
    private int driverNumber;
    private final DriverService service;

    public DriverCommand(DriverService service) { this.service = service; }

    @Override
    public void run() {
        System.out.println(service.get(driverNumber));
    }
}
