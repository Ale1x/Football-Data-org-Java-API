package dev.passarelli.controller;

import dev.passarelli.model.Match;
import dev.passarelli.service.ApiService;
import dev.passarelli.utils.JsonManager;

import java.io.IOException;
import java.util.List;

public class MatchController {

    private final ApiService apiService;
    private final JsonManager jsonManager;

    public MatchController () {
        this.apiService = new ApiService();
        this.jsonManager = new JsonManager();
    }


    public MatchController(ApiService apiService, JsonManager jsonManager) {
        this.apiService = apiService;
        this.jsonManager = jsonManager;
    }

    public List<Match> getTodayMatches() throws IOException, InterruptedException {
        String apiResponse = apiService.sendGetRequest("matches");

        return jsonManager.deserializeMatches(apiResponse);

    }

    public List<Match> getMatchesByCompetition(String code) throws IOException, InterruptedException {
        String apiResponse = apiService.sendGetRequest("matches?competitions=" + code);

        return jsonManager.deserializeMatches(apiResponse);
    }

    public Match getMatchDetails(String id) throws IOException, InterruptedException {
        String apiResponse = apiService.sendGetRequest("matches/" + id);

        return jsonManager.deserializeMatch(apiResponse);
    }
}
