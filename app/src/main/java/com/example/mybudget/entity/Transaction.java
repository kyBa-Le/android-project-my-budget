package com.example.mybudget.entity;

public class Transaction {
    private Integer id;
    private long amount;
    private int category_id;
    private String type;
    private String description;
    private String date;

    public Transaction() {
    }

    public Transaction(Integer id,
                       long amount,
                       int category_id,
                       String type,
                       String description,
                       String date) {
        this.id = id;
        this.amount = amount;
        this.category_id = category_id;
        this.type = type;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
