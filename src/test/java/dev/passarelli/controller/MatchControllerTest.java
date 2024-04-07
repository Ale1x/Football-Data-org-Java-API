package dev.passarelli.controller;

import dev.passarelli.controller.MatchController;
import dev.passarelli.model.Match;
import dev.passarelli.service.ApiService;
import dev.passarelli.utils.JsonManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class MatchControllerTest {

    private MatchController matchController;
    private ApiService apiServiceMock;
    private JsonManager jsonManagerMock;

    @BeforeEach
    public void setup() {
        apiServiceMock = Mockito.mock(ApiService.class);
        jsonManagerMock = Mockito.mock(JsonManager.class);
        matchController = new MatchController(apiServiceMock, jsonManagerMock);
    }

    @Test
    public void getMatchesByCompetitionReturnsDeserializedMatches() throws IOException, InterruptedException {
        String code = "1";
        String apiResponse = "{...}";
        List<Match> expectedMatches = Collections.emptyList();

        when(apiServiceMock.sendGetRequest("matches?competitions=" + code)).thenReturn(apiResponse);
        when(jsonManagerMock.deserializeMatches(apiResponse)).thenReturn(expectedMatches);

        List<Match> result = matchController.getMatchesByCompetition(code);

        assertEquals(expectedMatches, result);
    }

    @Test
    public void getMatchesByCompetitionThrowsExceptionWhenApiFails() throws IOException, InterruptedException {
        String code = "1";

        when(apiServiceMock.sendGetRequest("matches?competitions=" + code)).thenThrow(IOException.class);

        assertThrows(IOException.class, () -> matchController.getMatchesByCompetition(code));
    }

    @Test
    public void getMatchesByCompetitionThrowsExceptionWhenDeserializationFails() throws IOException, InterruptedException {
        String code = "1";
        String apiResponse = "{...}";

        when(apiServiceMock.sendGetRequest("matches?competitions=" + code)).thenReturn(apiResponse);
        when(jsonManagerMock.deserializeMatches(apiResponse)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> matchController.getMatchesByCompetition(code));
    }
}
