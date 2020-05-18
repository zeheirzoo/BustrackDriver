package com.example.driver.model;

import java.util.Objects;

public class VehicleType {


    private int id;
    private String name;

    public VehicleType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VehicleType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
