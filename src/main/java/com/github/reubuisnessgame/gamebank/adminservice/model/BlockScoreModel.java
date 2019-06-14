package com.github.reubuisnessgame.gamebank.adminservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "block_score")
public class BlockScoreModel {

    @Column(name = "teamId", unique = true, nullable = false)
    private long teamId;

    @Column(name = "rate", nullable = false)
    private double rate;

    public BlockScoreModel(long teamId, double rate) {
        this.teamId = teamId;
        this.rate = rate;
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
