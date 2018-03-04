package com.hackerrank.weather.entity;


import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "weather" )
public class WeatherEntity {


    @Id
    @GeneratedValue
    private long id;

    @Column(name="location_id")
    private long locationId;


    @Column(name="weather_id")
    private long weatherId;

    private Date date;

    private String temperature;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public long getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(long weatherId) {
        this.weatherId = weatherId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherEntity)) return false;
        WeatherEntity that = (WeatherEntity) o;
        return getId() == that.getId() &&
                getLocationId() == that.getLocationId() &&
                getWeatherId() == that.getWeatherId() &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getTemperature(), that.getTemperature());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getLocationId(), getWeatherId(), getDate(), getTemperature());
    }
}

