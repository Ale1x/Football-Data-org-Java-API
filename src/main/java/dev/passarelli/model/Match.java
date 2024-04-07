package dev.passarelli.model;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class Match {

    private int id;

    private Area area;

    private Competition competition;

    private Season season;

    private String status;

    private int matchday;

    private Team homeTeam;

    private Team awayTeam;

    private Score score;

    private String utcDate;

}
