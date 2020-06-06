package com.example.driver.model;

import java.io.Serializable;
import java.util.Date;

public class IntermediaryPoint implements Serializable {

    private int id;
    private int interstation_id;
    private Integer order;
    private double a_b_latitude;
    private double a_b_longitude;
    private String a_b_address;

    private double b_a_latitude;
    private double b_a_longitude;
    private String b_a_address;
    private Date create_at;

    public IntermediaryPoint(int id, int interstation_id, int order, double a_b_latitude, double a_b_longitude, String a_b_address, double b_a_latitude, double b_a_longitude, String b_a_address, Date create_at) {
        this.id = id;
        this.interstation_id = interstation_id;
        this.order = order;
        this.a_b_latitude = a_b_latitude;
        this.a_b_longitude = a_b_longitude;
        this.a_b_address = a_b_address;
        this.b_a_latitude = b_a_latitude;
        this.b_a_longitude = b_a_longitude;
        this.b_a_address = b_a_address;
        this.create_at = create_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInterstation_id() {
        return interstation_id;
    }

    public void setInterstation_id(int interstation_id) {
        this.interstation_id = interstation_id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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




    @Override
    public String toString() {
        return "IntermediaryPoint{" +
                "id=" + id +
                ", interstation_id=" + interstation_id +
                ", order=" + order +
                ", a_b_latitude=" + a_b_latitude +
                ", a_b_longitude=" + a_b_longitude +
                ", a_b_address='" + a_b_address + '\'' +
                ", b_a_latitude=" + b_a_latitude +
                ", b_a_longitude=" + b_a_longitude +
                ", b_a_address='" + b_a_address + '\'' +
                ", create_at=" + create_at +
                '}';
    }

//    @Override
//    public int compareTo(IntermediaryPoint o) {
//
//        return getOrder().compareTo(o.getOrder());
//    }
}
