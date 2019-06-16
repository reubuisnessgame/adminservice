package com.github.reubuisnessgame.gamebank.adminservice.model;

import javax.persistence.*;

@Entity
@Table(name = "admins")
public class AdminModel {

    @Id
    @GeneratedValue
    @Column(name = "admin_id")
    private Long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "max_score")
    private Double maxScore;

    @Column(name = "coefficient")
    private Double coefficient;

    public AdminModel() {
    }

    public AdminModel(long userId, String username, Role role, Double maxScore, Double coefficient) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.maxScore = maxScore;
        this.coefficient = coefficient;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }
}
