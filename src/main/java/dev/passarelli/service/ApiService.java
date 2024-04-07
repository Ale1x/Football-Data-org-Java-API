package dev.passarelli.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.io.IOException;

public class ApiService {
    private static final String BASE_URL = "https://api.football-data.org/v4/";
    private static final String API_TOKEN = "e8d0f47de9de4fec8a07a4389921f59c";

    private final HttpClient httpClient;

    public ApiService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public ApiService(HttpClient httpClientMock) {
        this.httpClient = httpClientMock;
    }

    public String sendGetRequest(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("X-Auth-Token", API_TOKEN)
                .build();
        return sendRequest(request);
    }

    public String sendPostRequest(String endpoint, String requestBody) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("X-Auth-Token", API_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        return sendRequest(request);
    }

    private String sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        int statusCode = response.statusCode();
        if (statusCode >= 200 && statusCode < 300) {
            return response.body();
        } else {
            throw new IOException("HTTP Request fallita con il codice: " + statusCode);
        }
    }
}
