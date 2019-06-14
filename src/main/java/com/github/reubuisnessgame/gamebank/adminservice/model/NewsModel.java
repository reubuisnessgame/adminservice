package com.github.reubuisnessgame.gamebank.adminservice.model;

import javax.persistence.*;

@Entity
@Table(name = "news")
public class NewsModel {

    @Id
    @GeneratedValue
    @Column(name = "news_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "heading")
    private String heading;

    @Column(name = "article")
    private String article;

    @Column(name = "create_date")
    private String createDate;

    public NewsModel() {
    }

    public NewsModel(String heading, String article, String createDate) {
        this.heading = heading;
        this.article = article;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public String getHeading() {
        return heading;
    }

    public String getArticle() {
        return article;
    }

    public String getCreateDate() {
        return createDate;
    }
}
