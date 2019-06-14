package com.github.reubuisnessgame.gamebank.adminservice.form;


import com.github.reubuisnessgame.gamebank.adminservice.model.TeamModel;

public class AllTeamsForm {
    private Iterable<TeamModel> teams;

    public AllTeamsForm() {
    }

    public AllTeamsForm(Iterable<TeamModel> teams) {
        this.teams = teams;
    }

    public Iterable<TeamModel> getTeams() {
        return teams;
    }

    public void setTeams(Iterable<TeamModel> teams) {
        this.teams = teams;
    }
}
