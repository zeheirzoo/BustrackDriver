package com.example.driver.model;

import java.util.Date;
import java.util.List;

public class InterStation {

    private int id;
    private int src_id;
    private int dest_id;
    private int a_b_time;
    private double a_b_distance;
    private int b_a_time;
    private double b_a_distance;
    private int current_station_id;
    private int current_intermediary_point_id;
    private List<IntermediaryPoint> intermediary_point;


    public InterStation(int id, int src_id, int dest_id, int a_b_time, double a_b_distance, int b_a_time, double b_a_distance, int current_station_id, int current_intermediary_point_id, List<IntermediaryPoint> intermediary_point) {
        this.id = id;
        this.src_id = src_id;
        this.dest_id = dest_id;
        this.a_b_time = a_b_time;
        this.a_b_distance = a_b_distance;
        this.b_a_time = b_a_time;
        this.b_a_distance = b_a_distance;
        this.current_station_id = current_station_id;
        this.current_intermediary_point_id = current_intermediary_point_id;
        this.intermediary_point = intermediary_point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSrc_id() {
        return src_id;
    }

    public void setSrc_id(int src_id) {
        this.src_id = src_id;
    }

    public int getDest_id() {
        return dest_id;
    }

    public void setDest_id(int dest_id) {
        this.dest_id = dest_id;
    }

    public int getA_b_time() {
        return a_b_time;
    }

    public void setA_b_time(int a_b_time) {
        this.a_b_time = a_b_time;
    }

    public double getA_b_distance() {
        return a_b_distance;
    }

    public void setA_b_distance(double a_b_distance) {
        this.a_b_distance = a_b_distance;
    }

    public int getB_a_time() {
        return b_a_time;
    }

    public void setB_a_time(int b_a_time) {
        this.b_a_time = b_a_time;
    }

    public double getB_a_distance() {
        return b_a_distance;
    }

    public void setB_a_distance(double b_a_distance) {
        this.b_a_distance = b_a_distance;
    }

    public int getCurrent_station_id() {
        return current_station_id;
    }

    public void setCurrent_station_id(int current_station_id) {
        this.current_station_id = current_station_id;
    }

    public int getCurrent_intermediary_point_id() {
        return current_intermediary_point_id;
    }

    public void setCurrent_intermediary_point_id(int current_intermediary_point_id) {
        this.current_intermediary_point_id = current_intermediary_point_id;
    }

    public List<IntermediaryPoint> getIntermediary_point() {
        return intermediary_point;
    }

    public void setIntermediary_point(List<IntermediaryPoint> intermediary_point) {
        this.intermediary_point = intermediary_point;
    }

    @Override
    public String toString() {
        return "InterStation{" +
                "id=" + id +
                ", src_id=" + src_id +
                ", dest_id=" + dest_id +
                ", a_b_time=" + a_b_time +
                ", a_b_distance=" + a_b_distance +
                ", b_a_time=" + b_a_time +
                ", b_a_distance=" + b_a_distance +
                ", current_station_id=" + current_station_id +
                ", current_intermediary_point_id=" + current_intermediary_point_id +
                ", intermediary_point=" + intermediary_point +
                '}';
    }
}