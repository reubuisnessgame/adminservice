package com.github.reubuisnessgame.gamebank.adminservice.form;

public class AddScoreForm {


    private Double rate;

    private Long teamNumber;

    private boolean isWin;

    public AddScoreForm(Double rate, Long teamNumber, boolean isWin) {
        this.rate = rate;
        this.teamNumber = teamNumber;
        this.isWin = isWin;
    }

    public Double getRate() {
        return rate;
    }

    public Long getTeamNumber() {
        return teamNumber;
    }

    public boolean isWin() {
        return isWin;
    }
}
