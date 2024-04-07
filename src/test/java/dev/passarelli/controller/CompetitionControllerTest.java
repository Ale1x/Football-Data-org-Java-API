package dev.passarelli.controller;

import dev.passarelli.controller.CompetitionController;
import dev.passarelli.model.Competition;
import dev.passarelli.service.ApiService;
import dev.passarelli.utils.JsonManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CompetitionControllerTest {

    private CompetitionController competitionController;
    private ApiService apiServiceMock;
    private JsonManager jsonManagerMock;

    @BeforeEach
    public void setup() {
        apiServiceMock = Mockito.mock(ApiService.class);
        jsonManagerMock = Mockito.mock(JsonManager.class);
        competitionController = new CompetitionController(apiServiceMock, jsonManagerMock);
    }

    @Test
    public void getCompetitionByIdReturnsDeserializedCompetition() throws IOException, InterruptedException {
        String id = "1";
        String apiResponse = "{...}";
        Competition expectedCompetition = new Competition();

        when(apiServiceMock.sendGetRequest("competitions/" + id)).thenReturn(apiResponse);
        when(jsonManagerMock.deserializeCompetition(apiResponse)).thenReturn(expectedCompetition);

        Competition result = competitionController.getCompetitionById(id);

        assertEquals(expectedCompetition, result);
    }

    @Test
    public void getCompetitionByIdThrowsExceptionWhenApiFails() throws IOException, InterruptedException {
        String id = "1";

        when(apiServiceMock.sendGetRequest("competitions/" + id)).thenThrow(IOException.class);

        assertThrows(IOException.class, () -> competitionController.getCompetitionById(id));
    }

    @Test
    public void getCompetitionByIdThrowsExceptionWhenDeserializationFails() throws IOException, InterruptedException {
        String id = "1";
        String apiResponse = "{...}";

        when(apiServiceMock.sendGetRequest("competitions/" + id)).thenReturn(apiResponse);
        when(jsonManagerMock.deserializeCompetition(apiResponse)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> competitionController.getCompetitionById(id));
    }
}