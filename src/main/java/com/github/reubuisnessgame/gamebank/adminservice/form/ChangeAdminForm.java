package com.github.reubuisnessgame.gamebank.adminservice.form;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangeAdminForm {

    private String lastUsername;

    private String newUsername;

    private String newPassword;

    private String newRole;

    private double newMaxScore;

    private double newCoefficient;

    @JsonCreator
    public ChangeAdminForm(@JsonProperty("lastUsername") String lastUsername,@JsonProperty("newUsername") String newUsername,
                           @JsonProperty("newPassword") String newPassword, @JsonProperty("newRole") String newRole,
                           @JsonProperty("newMaxScore") double newMaxScore, @JsonProperty("newCoefficient") double newCoefficient) {
        this.lastUsername = lastUsername;
        this.newUsername = newUsername;
        this.newPassword = newPassword;
        this.newRole = newRole;
        this.newMaxScore = newMaxScore;
        this.newCoefficient = newCoefficient;
    }

    public String getLastUsername() {
        return lastUsername;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewRole() {
        return newRole;
    }

    public double getNewMaxScore() {
        return newMaxScore;
    }

    public double getNewCoefficient() {
        return newCoefficient;
    }
}
