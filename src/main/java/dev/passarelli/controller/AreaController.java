package dev.passarelli.controller;

import dev.passarelli.model.Area;
import dev.passarelli.service.ApiService;
import dev.passarelli.utils.JsonManager;

import java.io.IOException;

public class AreaController {

    private final ApiService apiService;
    private final JsonManager jsonManager;

    public AreaController(ApiService apiService, JsonManager jsonManager) {
        this.apiService = apiService;
        this.jsonManager = jsonManager;
    }
    public AreaController () {
        this.apiService = new ApiService();
        this.jsonManager = new JsonManager();
    }

    public Area getData(String id) throws IOException, InterruptedException {
        String apiResponse = apiService.sendGetRequest("areas/" + id);

        return jsonManager.deserializeArea(apiResponse);

    }
}
