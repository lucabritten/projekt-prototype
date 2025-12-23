package com.britten;

import com.britten.cli.F1Cli;
import picocli.CommandLine;

public class App 
{
    public static void main( String[] args ) throws Exception{
        int exitCode = new CommandLine(new F1Cli()).execute(args);

      //  System.out.println(new OpenF1Client().getLapsForSession("Singapore", 2025, 44, SessionType.RACE));
    }
}
