package dev.passarelli.wrappers;

import dev.passarelli.model.Match;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MatchesResponse {

    private List<Match> matches;

}
