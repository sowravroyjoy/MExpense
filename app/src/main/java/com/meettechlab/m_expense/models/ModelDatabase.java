package com.meettechlab.m_expense.models;

public class ModelDatabase {
    private int trip_id;
    private String name;
    private String destination;
    private String date;
    private String risk;
    private String description;

    public ModelDatabase(int trip_id, String name, String destination, String date, String risk, String description) {
        this.trip_id = trip_id;
        this.name = name;
        this.destination = destination;
        this.date = date;
        this.risk = risk;
        this.description = description;
    }

    public ModelDatabase() {
    }

    public ModelDatabase(int trip_id, String name) {
        this.trip_id = trip_id;
        this.name = name;
    }

    public ModelDatabase(String name, String destination, String date) {
        this.name = name;
        this.destination = destination;
        this.date = date;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
