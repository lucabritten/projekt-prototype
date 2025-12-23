package com.britten.cli;

import com.britten.infrastructure.ApplicationContext;
import com.britten.service.DriverService;
import picocli.CommandLine;

/**
 * Jeder cmd muss einfach das Runnable Interface implementieren
 * Mit der @Command Annotation kann der Command definiert werden
 * mit @Option oder @Parameter können Optionen definiert werden
 */
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

    private final DriverService service = ApplicationContext.driverService();

    @Override
    public void run() {
        System.out.println(service.get(driverNumber));
    }
}
