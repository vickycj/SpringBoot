package com.hackerrank.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class WeatherJson {


    @JsonProperty("id")
    private Long id;

    @JsonProperty("date")
    private String dateRecorded;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("temperature")
    private Float[] temperature;

    public WeatherJson() {
    }

    public WeatherJson(Long id, String dateRecorded, Location location, Float[] temperature) {
        this.id = id;
        this.dateRecorded = dateRecorded;
        this.location = location;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(String dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Float[] getTemperature() {
        return temperature;
    }

    public void setTemperature(Float[] temperature) {
        this.temperature = temperature;
    }
}
