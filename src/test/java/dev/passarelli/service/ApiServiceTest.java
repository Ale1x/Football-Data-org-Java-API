package dev.passarelli.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ApiServiceTest {

    private ApiService apiService;
    private HttpClient httpClientMock;

    private static final String BASE_URL = "https://api.football-data.org/v4/";
    private static final String API_TOKEN = "e8d0f47de9de4fec8a07a4389921f59c";

    @BeforeEach
    public void setup() {
        httpClientMock = Mockito.mock(HttpClient.class);
        apiService = new ApiService(httpClientMock);
    }

    @Test
    public void sendGetRequestThrowsExceptionWhenRequestFails() throws IOException, InterruptedException {
        String endpoint = "testEndpoint";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("X-Auth-Token", API_TOKEN)
                .build();

        when(httpClientMock.send(request, HttpResponse.BodyHandlers.ofString()))
                .thenThrow(IOException.class);

        assertThrows(IOException.class, () -> apiService.sendGetRequest(endpoint));
    }

    @Test
    public void sendPostRequestThrowsExceptionWhenRequestFails() throws IOException, InterruptedException {
        String endpoint = "testEndpoint";
        String requestBody = "testRequestBody";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("X-Auth-Token", API_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        when(httpClientMock.send(request, HttpResponse.BodyHandlers.ofString()))
                .thenThrow(IOException.class);

        assertThrows(IOException.class, () -> apiService.sendPostRequest(endpoint, requestBody));
    }
}