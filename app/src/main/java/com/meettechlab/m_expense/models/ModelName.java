package com.meettechlab.m_expense.models;

public class ModelName {
    private int id;
    private String TripName;

    public ModelName() {
    }

    public ModelName(int id, String tripName) {
        this.id = id;
        TripName = tripName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTripName() {
        return TripName;
    }

    public void setTripName(String tripName) {
        TripName = tripName;
    }
}


