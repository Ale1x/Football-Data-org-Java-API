package dev.passarelli.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Competition {

    private int id;

    private String name;

    private String code;

    private String type;

    private String emblem;

    private Season currentSeason;
}
