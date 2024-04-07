package dev.passarelli.controller;

import dev.passarelli.controller.TeamController;
import dev.passarelli.model.Team;
import dev.passarelli.service.ApiService;
import dev.passarelli.utils.JsonManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class TeamControllerTest {

    private TeamController teamController;
    private ApiService apiServiceMock;
    private JsonManager jsonManagerMock;

    @BeforeEach
    public void setup() {
        apiServiceMock = Mockito.mock(ApiService.class);
        jsonManagerMock = Mockito.mock(JsonManager.class);
        teamController = new TeamController(apiServiceMock, jsonManagerMock);
    }

    @Test
    public void getDataReturnsDeserializedTeam() throws IOException, InterruptedException {
        String id = "1";
        String apiResponse = "{...}";
        Team expectedTeam = new Team();

        when(apiServiceMock.sendGetRequest("teams/" + id)).thenReturn(apiResponse);
        when(jsonManagerMock.deserializeTeam(apiResponse)).thenReturn(expectedTeam);

        Team result = teamController.getData(id);

        assertEquals(expectedTeam, result);
    }

    @Test
    public void getDataThrowsExceptionWhenApiFails() throws IOException, InterruptedException {
        String id = "1";

        when(apiServiceMock.sendGetRequest("teams/" + id)).thenThrow(IOException.class);

        assertThrows(IOException.class, () -> teamController.getData(id));
    }

    @Test
    public void getDataThrowsExceptionWhenDeserializationFails() throws IOException, InterruptedException {
        String id = "1";
        String apiResponse = "{...}";

        when(apiServiceMock.sendGetRequest("teams/" + id)).thenReturn(apiResponse);
        when(jsonManagerMock.deserializeTeam(apiResponse)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> teamController.getData(id));
    }
}