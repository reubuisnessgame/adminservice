package com.github.reubuisnessgame.gamebank.adminservice.model;

import javax.persistence.*;

@Entity
@Table(name = "block_score")
public class BlockScoreModel {

    @Id
    @GeneratedValue
    @Column(name = "score_id")
    private long id;

    @Column(name = "teamId", unique = true, nullable = false)
    private long teamId;

    @Column(name = "rate", nullable = false)
    private double rate;

    public BlockScoreModel(long teamId, double rate) {
        this.teamId = teamId;
        this.rate = rate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
