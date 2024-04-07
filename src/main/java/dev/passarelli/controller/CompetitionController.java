package dev.passarelli.controller;

import dev.passarelli.model.Competition;
import dev.passarelli.model.Match;
import dev.passarelli.service.ApiService;
import dev.passarelli.utils.JsonManager;

import java.io.IOException;
import java.util.List;

public class CompetitionController {

    private ApiService apiService;
    private JsonManager jsonManager;

    public CompetitionController () {
        this.apiService = new ApiService();
        this.jsonManager = new JsonManager();
    }

    public CompetitionController(ApiService apiService, JsonManager jsonManager) {
        this.apiService = apiService;
        this.jsonManager = jsonManager;
    }

    public Competition getCompetitionById(String id) throws IOException, InterruptedException {
        String apiResponse = apiService.sendGetRequest("competitions/" + id);

        return jsonManager.deserializeCompetition(apiResponse);

    }

    public List<Competition> getCompetitions() throws IOException, InterruptedException {
        String apiResponse = apiService.sendGetRequest("competitions");

        return jsonManager.deserializeCompetitions(apiResponse);
    }
}
