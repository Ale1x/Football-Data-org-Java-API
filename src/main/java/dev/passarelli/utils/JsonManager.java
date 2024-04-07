package dev.passarelli.utils;

import com.google.gson.Gson;
import dev.passarelli.model.*;
import dev.passarelli.wrappers.CompetitionsResponse;
import dev.passarelli.wrappers.MatchesResponse;

import java.util.List;

public class JsonManager {
    private final Gson gson;

    public JsonManager() {
        this.gson = new Gson();
    }

    public Team deserializeTeam(String json) {
        return gson.fromJson(json, Team.class);
    }

    public Area deserializeArea(String json) {
        return gson.fromJson(json, Area.class);
    }

    public Competition deserializeCompetition(String json) {
        return gson.fromJson(json, Competition.class);
    }

    public List<Match> deserializeMatches(String json) {
        MatchesResponse response = gson.fromJson(json, MatchesResponse.class);
        return response.getMatches();
    }

    public Match deserializeMatch(String json) {
        return gson.fromJson(json, Match.class);
    }

    public List<Competition> deserializeCompetitions(String json) {
        CompetitionsResponse response = gson.fromJson(json, CompetitionsResponse.class);
        return response.getCompetitions();
    }
}