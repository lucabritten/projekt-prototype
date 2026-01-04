package com.britten;

import com.britten.cli.F1Cli;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import picocli.CommandLine;

@SpringBootApplication
public class App {

    public static void main(String[] args) {

        ApplicationContext context =
                SpringApplication.run(App.class, args);

        F1Cli rootCommand = context.getBean(F1Cli.class);
        CommandLine.IFactory factory =
                context.getBean(CommandLine.IFactory.class);

        int exitCode =
                new CommandLine(rootCommand, factory).execute(args);

        System.exit(exitCode);
    }
}