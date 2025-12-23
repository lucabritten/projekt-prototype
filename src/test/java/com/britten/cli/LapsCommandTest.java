package com.britten.cli;

import com.britten.domain.Lap;
import com.britten.domain.SessionType;
import com.britten.infrastructure.OpenF1Client;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LapsCommandTest {

    @Test
    void shouldExecuteLapsCommand(){
        OpenF1Client fakeClient = Mockito.mock(OpenF1Client.class);

        Mockito.when(
                fakeClient.getLapsForSession(
                        "Austria",
                        2025,
                        1,
                        SessionType.RACE
                )
        ).thenReturn(List.of(
                new Lap(1, 83.4f, 1),
                new Lap(1, 84.2f,2)
        ));
        LapsAllCommand command = new LapsAllCommand(fakeClient);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        new CommandLine(command).execute(
                "--year", "2025",
                "--country", "Austria",
                "--driver", "1"
        );

        String output = outputStream.toString();

        assertThat(output.contains("Lap")).isTrue();
        assertThat(output.contains("83.4")).isTrue();
    }
}
