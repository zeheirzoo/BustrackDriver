package com.example.driver.model;

import java.util.Set;

public class Driver {

    int id;
    String drivinglicense;
    User user;
    String token;
//    Set<Ride> ride;

    public Driver(int id, String drivinglicense, User user, String token) {
        this.id = id;
        this.drivinglicense = drivinglicense;
        this.user = user;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Driver(int id, String drivinglicense) {
        this.id = id;
        this.drivinglicense = drivinglicense;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userl) {
        this.user = userl;
    }

    public String getDrivinglicense() {
        return drivinglicense;
    }

    public void setDrivinglicense(String drivinglicense) {
        this.drivinglicense = drivinglicense;
    }
//


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", drivinglicense=" + drivinglicense +
                ", user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}
