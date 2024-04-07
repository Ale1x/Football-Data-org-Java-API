package dev.passarelli.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class Season {

    private int id;

    private String startDate;

    private String endDate;

    private int currentMatchday;
}
