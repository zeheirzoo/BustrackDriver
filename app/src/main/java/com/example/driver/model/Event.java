package com.example.driver.model;

import java.util.Date;

public class Event {

//     "id": 1,
////             "name": "Salat Aid",
////             "type": "information",
////             "description": "Aid Mosque Amir",
////             "start_at": "2020-05-23 07:00:00.000000",
////             "end_at": "2020-05-23 07:40:00.000000",
////             "eventable_id": null,
////             "eventable_type": null,
////             "latitude": 36.34689999999999798774297232739627361297607421875,
////             "longitude": 6.60320000000000018047785488306544721126556396484375,
////             "address": null,
////             "created_at": "2020-05-15 19:06:30.000000"


    private  int id;
    private  String name;
    private  String type;
    private  String description;
    private Date start_at;
    private Date end_at;
    private  int eventable_id;
    private  String eventable_type;

    private String address;
    private double latitude,longitude;
    private Date created_at;

    public Event(int id, String name, String type,
                 String description, Date start_at, Date end_at,
                 int eventable_id, String eventable_type, String address,
                 double latitude, double longitude, Date created_at) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.start_at = start_at;
        this.end_at = end_at;
        this.eventable_id = eventable_id;
        this.eventable_type = eventable_type;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created_at = created_at;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_at() {
        return start_at;
    }

    public void setStart_at(Date start_at) {
        this.start_at = start_at;
    }

    public Date getEnd_at() {
        return end_at;
    }

    public void setEnd_at(Date end_at) {
        this.end_at = end_at;
    }

    public int getEventable_id() {
        return eventable_id;
    }

    public void setEventable_id(int eventable_id) {
        this.eventable_id = eventable_id;
    }

    public String getEventable_type() {
        return eventable_type;
    }

    public void setEventable_type(String eventable_type) {
        this.eventable_type = eventable_type;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", start_at=" + start_at +
                ", end_at=" + end_at +
                ", eventable_id=" + eventable_id +
                ", eventable_type='" + eventable_type + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", created_at=" + created_at +
                '}';
    }
}
