package ru.eltech.shared;

import java.io.Serializable;

public class FootballersTeam implements Serializable {
    private static final long serialVersionUID = 1L;
    private String team;

    public FootballersTeam() {
    }

    public FootballersTeam(String _team) {
        this.team = _team;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}