package dev.passarelli.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Team {

    private int id;

    private String name;

    private String shortName;

    private String website;

    private String crest;

    private int founded;

    private List<Competition> runningCompetitions;

    private int marketValue;

}