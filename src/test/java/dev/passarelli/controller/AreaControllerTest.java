package dev.passarelli.controller;

import dev.passarelli.controller.AreaController;
import dev.passarelli.model.Area;
import dev.passarelli.service.ApiService;
import dev.passarelli.utils.JsonManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class AreaControllerTest {

    private AreaController areaController;
    private ApiService apiServiceMock;
    private JsonManager jsonManagerMock;

    @BeforeEach
    public void setup() {
        apiServiceMock = Mockito.mock(ApiService.class);
        jsonManagerMock = Mockito.mock(JsonManager.class);
        areaController = new AreaController(apiServiceMock, jsonManagerMock);
    }

    @Test
    public void getDataReturnsDeserializedArea() throws IOException, InterruptedException {
        String id = "1";
        String apiResponse = "{...}";
        Area expectedArea = new Area();

        when(apiServiceMock.sendGetRequest("areas/" + id)).thenReturn(apiResponse);
        when(jsonManagerMock.deserializeArea(apiResponse)).thenReturn(expectedArea);

        Area result = areaController.getData(id);

        assertEquals(expectedArea, result);
    }

    @Test
    public void getDataThrowsExceptionWhenApiFails() throws IOException, InterruptedException {
        String id = "1";

        when(apiServiceMock.sendGetRequest("areas/" + id)).thenThrow(IOException.class);

        assertThrows(IOException.class, () -> areaController.getData(id));
    }

    @Test
    public void getDataThrowsExceptionWhenDeserializationFails() throws IOException, InterruptedException {
        String id = "1";
        String apiResponse = "{...}";

        when(apiServiceMock.sendGetRequest("areas/" + id)).thenReturn(apiResponse);
        when(jsonManagerMock.deserializeArea(apiResponse)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> areaController.getData(id));
    }
}