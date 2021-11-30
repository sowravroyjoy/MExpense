package com.meettechlab.m_expense.models;

public class ModelExpense {
    private int id;
    private String type;
    private String amount;
    private String time;
    private String comment;
    private int tripID;

    public ModelExpense(int id, String type, String amount, String time, String comment, int tripID) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.time = time;
        this.comment = comment;
        this.tripID = tripID;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }
}
