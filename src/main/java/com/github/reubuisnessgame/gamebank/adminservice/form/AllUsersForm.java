package com.github.reubuisnessgame.gamebank.adminservice.form;


import com.github.reubuisnessgame.gamebank.adminservice.model.AdminModel;
import com.github.reubuisnessgame.gamebank.adminservice.model.TeamModel;

public class AllUsersForm {
    private Iterable<TeamModel> teams;

    private Iterable<AdminModel> admins;

    public AllUsersForm() {
    }

    public Iterable<TeamModel> getTeams() {
        return teams;
    }

    public void setTeams(Iterable<TeamModel> teams) {
        this.teams = teams;
    }

    public Iterable<AdminModel> getAdmins() {
        return admins;
    }

    public void setAdmins(Iterable<AdminModel> admins) {
        this.admins = admins;
    }
}
