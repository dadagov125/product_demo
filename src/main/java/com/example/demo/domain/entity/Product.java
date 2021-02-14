package com.example.demo.domain.entity;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    @Id
    private Integer id;

    private String code;

    private String name;

    private BigDecimal price;

    private String article;

    private Date produced;

    private Integer count;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Date getProduced() {
        return produced;
    }

    public void setProduced(Date produced) {
        this.produced = produced;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
