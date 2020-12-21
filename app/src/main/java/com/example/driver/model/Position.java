package com.example.driver.model;

import java.io.Serializable;
import java.util.Date;

public class Position implements Serializable {



    private int id;
    private String address;
    private double latitude,longitude;

    public Position(int id, String address, double latitude, double longitude, Date created_at) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Position(double latitude, double longitude) {
        this.address = "address";
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }



    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}'+"\n";
    }
}
