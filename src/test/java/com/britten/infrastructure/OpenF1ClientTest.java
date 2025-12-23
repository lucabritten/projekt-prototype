package com.britten.infrastructure;

import com.britten.domain.Driver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenF1ClientTest {

    private MockWebServer mockWebServer;
    private OpenF1Client client;

    @BeforeEach
    void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        client = new OpenF1Client(mockWebServer.url("/v1").toString());
    }

    @AfterEach
    void tearDown() throws IOException{
        mockWebServer.close();
    }

    @Test
    void shouldReturnDriverByNumber() {
        String json = """
                [
                    {
                        "driver_number": 1,
                        "country_code": "",
                        "full_name": "MAX VERSTAPPEN"
                    }
                ]
                """;

        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody(json)
                .setResponseCode(200)
        );

        Driver driver = client.getDriverByNumber(1);

        assertThat(driver.number()).isEqualTo(1);
        assertThat(driver.name()).isEqualTo("MAX VERSTAPPEN");
    }


}
