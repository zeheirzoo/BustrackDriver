package com.example.driver.model;

import java.io.Serializable;
import java.util.Date;

public class Ride implements Serializable {
//
    private int id;
    private int driver_id;
    private int vehicle_id;
    private int line_id;
    private Integer current_station_id;
    private Integer current_intermediary_point_id;
    private String direction;
    private Date departure_date;
    private Date finish_date;
    private Date created_at;
    private Line line;
    private Vehicle vehicle;
    private Station station;
    private IntermediaryPoint intermediary_point;

    public Ride( int driver_id,
                int vehicle_id, int line_id,
                int current_station_id,
                String direction, Date departure_date,
                Date finish_date, Date created_at) {

        this.driver_id = driver_id;
        this.vehicle_id = vehicle_id;
        this.line_id = line_id;
        this.current_station_id = current_station_id;
        this.direction = direction;
        this.departure_date = departure_date;
        this.finish_date = finish_date;
        this.created_at = created_at;
    }

    public Ride(int id, int driver_id, int vehicle_id, int line_id, int current_station_id, Integer current_intermediary_point_id, String direction, Date departure_date, Date finish_date, Date created_at, Line line, Vehicle vehicle, Station station, IntermediaryPoint intermediary_point) {
        this.id = id;
        this.driver_id = driver_id;
        this.vehicle_id = vehicle_id;
        this.line_id = line_id;
        this.current_station_id = current_station_id;
        this.current_intermediary_point_id = current_intermediary_point_id;
        this.direction = direction;
        this.departure_date = departure_date;
        this.finish_date = finish_date;
        this.created_at = created_at;
        this.line = line;
        this.vehicle = vehicle;
        this.station = station;
        this.intermediary_point = intermediary_point;
    }

    public Ride(){

    }
//    public Ride(int driver_id, int vehicle_id, int line_id, String direction) {
//        this.driver_id = driver_id;
//        this.vehicle_id = vehicle_id;
//        this.line_id = line_id;
//        this.direction = direction;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public int getLine_id() {
        return line_id;
    }

    public void setLine_id(int line_id) {
        this.line_id = line_id;
    }

    public int getCurrent_station_id() {
        return current_station_id;
    }

    public void setCurrent_station_id(int current_station_id) {
        this.current_station_id = current_station_id;
    }

    public Integer getCurrent_intermediary_point_id() {
        return current_intermediary_point_id;
    }

    public void setCurrent_intermediary_point_id(Integer current_intermediary_point_id) {
        this.current_intermediary_point_id = current_intermediary_point_id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Date getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(Date departure_date) {
        this.departure_date = departure_date;
    }

    public Date getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(Date finish_date) {
        this.finish_date = finish_date;
    }

    public Date getCreate_at() {
        return created_at;
    }

    public void setCreate_at(Date created_at) {
        this.created_at = created_at;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public IntermediaryPoint getIntermediary_point() {
        return intermediary_point;
    }

    public void setIntermediary_point(IntermediaryPoint intermediary_point) {
        this.intermediary_point = intermediary_point;
    }


    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", driver_id=" + driver_id +
                ", vehicle_id=" + vehicle_id +
                ", line_id=" + line_id +
                ", current_station_id=" + current_station_id +
                ", current_intermediary_point_id=" + current_intermediary_point_id +
                ", direction='" + direction + '\'' +
                ", departure_date=" + departure_date +
                ", finish_date=" + finish_date +
                ", created_at=" + created_at +
                ", line=" + line +
                ", vehicle=" + vehicle +
                ", station=" + station +
                ", intermediary_point=" + intermediary_point +
                '}';
    }
}
