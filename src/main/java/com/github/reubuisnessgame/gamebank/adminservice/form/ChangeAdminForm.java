package com.github.reubuisnessgame.gamebank.adminservice.form;

public class ChangeAdminForm {

    private String lastUsername;

    private String newUsername;

    private String newPassword;

    private String newRole;

    private double newMaxScore;

    private double newCoefficient;

    public ChangeAdminForm(String lastUsername, String newUsername, String newPassword, String newRole, double newMaxScore, double newCoefficient) {
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
