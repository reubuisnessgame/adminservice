package com.github.reubuisnessgame.gamebank.adminservice.form;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddScoreForm {

    private double rate;

    private long teamNumber;

    private boolean isWin;

    @JsonCreator
    public AddScoreForm(@JsonProperty("rate") double rate,
                        @JsonProperty("teamNumber") long teamNumber,
                        @JsonProperty("isWin") boolean isWin) {
        this.rate = rate;
        this.teamNumber = teamNumber;
        this.isWin = isWin;
    }

    public double getRate() {
        return rate;
    }

    public long getTeamNumber() {
        return teamNumber;
    }

    public boolean isWin() {
        return isWin;
    }
}
