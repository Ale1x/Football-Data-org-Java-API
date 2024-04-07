package dev.passarelli.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Score {


    private String winner;

    private HalfTimeResult fullTime;

    private HalfTimeResult halfTime;
}
