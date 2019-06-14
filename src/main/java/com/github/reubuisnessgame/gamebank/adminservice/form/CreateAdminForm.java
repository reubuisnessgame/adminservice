package com.github.reubuisnessgame.gamebank.adminservice.form;

public class CreateAdminForm {

    private String username;

    private String password;

    private String role;

    private double maxScore;

    private double coefficient;

    public CreateAdminForm(String username, String password, String role, double maxScore, double coefficient) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.maxScore = maxScore;
        this.coefficient = coefficient;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public double getCoefficient() {
        return coefficient;
    }
}
