package com.example.driver.model;

import java.util.Date;
import java.util.List;

public class Station {
/*
*  "id": 1,
    "name": "Ziayadia",
    "a_b_latitude": 36.37545999999999679630491300486028194427490234375,
    "a_b_longitude": 6.63656000000000023675283955526538193225860595703125,
    "a_b_address": null,
    "b_a_latitude": 36.37545999999999679630491300486028194427490234375,
    "b_a_longitude": 6.63656000000000023675283955526538193225860595703125,
    "b_a_address": null,
    "created_at": "2020-05-15 18:25:25.000000",*/

    private int id;
    private String name;


//    ab Position
    private double a_b_latitude;
    private double a_b_longitude;
    private String a_b_address;


//    b-a Position
    private double b_a_latitude;
    private double b_a_longitude;
    private String b_a_address;

    private Date create_at;



    private List<InterStation> srcinterstation;
    private  List<InterStation>destinterstation;


    public Station(int id, String name, double a_b_latitude,
                   double a_b_longitude, String a_b_address,
                   double b_a_latitude, double b_a_longitude,
                   String b_a_address, Date create_at,
                   List<InterStation> srcinterstation, List<InterStation> destinterstation) {
        this.id = id;
        this.name = name;
        this.a_b_latitude = a_b_latitude;
        this.a_b_longitude = a_b_longitude;
        this.a_b_address = a_b_address;
        this.b_a_latitude = b_a_latitude;
        this.b_a_longitude = b_a_longitude;
        this.b_a_address = b_a_address;
        this.create_at = create_at;
        this.srcinterstation = srcinterstation;
        this.destinterstation = destinterstation;
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

    public double getA_b_latitude() {
        return a_b_latitude;
    }

    public void setA_b_latitude(double a_b_latitude) {
        this.a_b_latitude = a_b_latitude;
    }

    public double getA_b_longitude() {
        return a_b_longitude;
    }

    public void setA_b_longitude(double a_b_longitude) {
        this.a_b_longitude = a_b_longitude;
    }

    public String getA_b_address() {
        return a_b_address;
    }

    public void setA_b_address(String a_b_address) {
        this.a_b_address = a_b_address;
    }

    public double getB_a_latitude() {
        return b_a_latitude;
    }

    public void setB_a_latitude(double b_a_latitude) {
        this.b_a_latitude = b_a_latitude;
    }

    public double getB_a_longitude() {
        return b_a_longitude;
    }

    public void setB_a_longitude(double b_a_longitude) {
        this.b_a_longitude = b_a_longitude;
    }

    public String getB_a_address() {
        return b_a_address;
    }

    public void setB_a_address(String b_a_address) {
        this.b_a_address = b_a_address;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public List<InterStation> getSrcinterstation() {
        return srcinterstation;
    }

    public void setSrcinterstation(List<InterStation> srcinterstation) {
        this.srcinterstation = srcinterstation;
    }

    public List<InterStation> getDestinterstation() {
        return destinterstation;
    }

    public void setDestinterstation(List<InterStation> destinterstation) {
        this.destinterstation = destinterstation;
    }






    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", a_b_latitude=" + a_b_latitude +
                ", a_b_longitude=" + a_b_longitude +
                ", a_b_address='" + a_b_address + '\'' +
                ", b_a_latitude=" + b_a_latitude +
                ", b_a_longitude=" + b_a_longitude +
                ", b_a_address='" + b_a_address + '\'' +
                ", create_at=" + create_at +
                ", srcinterstation=" + srcinterstation +
                ", destinterstation=" + destinterstation +
                '}';
    }
}
