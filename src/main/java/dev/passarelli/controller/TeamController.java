package dev.passarelli.controller;

import dev.passarelli.model.Team;
import dev.passarelli.service.ApiService;
import dev.passarelli.utils.JsonManager;

import java.io.IOException;

public class TeamController {

    private final ApiService apiService;
    private final JsonManager jsonManager;

    public TeamController () {
        this.apiService = new ApiService();
        this.jsonManager = new JsonManager();
    }

    public TeamController(ApiService apiService, JsonManager jsonManager) {
        this.apiService = apiService;
        this.jsonManager = jsonManager;
    }

    public Team getData(String id) throws IOException, InterruptedException {
        String apiResponse = apiService.sendGetRequest("teams/" + id);

        return jsonManager.deserializeTeam(apiResponse);

    }
}
