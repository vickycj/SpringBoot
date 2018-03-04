package com.hackerrank.weather.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "location")
public class LocationEntity {

    @Id
    @GeneratedValue
    private long id;

    private float latitude;

    private float longitude;

    private String city;

    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationEntity)) return false;
        LocationEntity that = (LocationEntity) o;
        return getId() == that.getId() &&
                Float.compare(that.getLatitude(), getLatitude()) == 0 &&
                Float.compare(that.getLongitude(), getLongitude()) == 0 &&
                Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getState(), that.getState());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getLatitude(), getLongitude(), getCity(), getState());
    }
}
