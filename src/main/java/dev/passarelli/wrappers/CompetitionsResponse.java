package dev.passarelli.wrappers;

import dev.passarelli.model.Competition;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CompetitionsResponse {

    private List<Competition> competitions;
}
