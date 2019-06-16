package com.github.reubuisnessgame.gamebank.adminservice.model;

import javax.persistence.*;

@Entity
@Table(name = "teams")
public class TeamModel {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "team_number", unique = true, nullable = false)
    private Long teamNumber;

    @Column(name = "score")
    private Double score;

    @Column(name = "full_score")
    private Double fullScore;

    @Column(name = "credit")
    private Double credit;

    @Column(name = "deposit")
    private Double deposit;

    @Column(name = "credit_time")
    private Long creditTime;

    @Column(name = "deposit_time")
    private Long depositTime;


    public TeamModel() {
    }

    public TeamModel(Long id, String username, Long teamNumber) {
        this.userId = id;
        this.username = username;
        this.teamNumber = teamNumber;
        this.score = 1100D;
        this.fullScore = 1100D;
        this.credit = 0D;
        this.deposit = 0D;
        this.creditTime = 0L;
        this.depositTime = 0L;
    }

    public TeamModel(Long id, Long teamNumber) {
        this.userId = id;
        this.teamNumber = teamNumber;
        this.username = teamNumber.toString();
        this.score = 1100D;
        this.fullScore = 1100D;
        this.credit = 0D;
        this.deposit = 0D;
        this.creditTime = 0L;
        this.depositTime = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(Long teamNumber) {
        this.teamNumber = teamNumber;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getFullScore() {
        return fullScore;
    }

    public void setFullScore(Double fullScore) {
        this.fullScore = fullScore;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Long getCreditTime() {
        return creditTime;
    }

    public void setCreditTime(Long creditTime) {
        this.creditTime = creditTime;
    }

    public Long getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(Long depositTime) {
        this.depositTime = depositTime;
    }
}
