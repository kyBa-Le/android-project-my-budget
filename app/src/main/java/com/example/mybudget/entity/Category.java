package com.example.mybudget.entity;

public class Category {
    private String category;
    private Integer id;

    public Category() {
    }

    public Category(String category, Integer id) {
        this.category = category;
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.category;
    }
}
