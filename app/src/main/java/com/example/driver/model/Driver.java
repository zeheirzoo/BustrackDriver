package com.example.driver.model;

import java.util.Set;

public class Driver {

    int id;
    int drivinglicense;
    User user;

    Set<Ride> ride;

    public Driver(int id, int drivinglicense) {
        this.id = id;
        this.drivinglicense = drivinglicense;
        this.ride = ride;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userl) {
        this.user = userl;
    }

    public int getDrivinglicense() {
        return drivinglicense;
    }

    public void setDrivinglicense(int drivinglicense) {
        this.drivinglicense = drivinglicense;
    }
//
    public Set<Ride> getRide() {
        return ride;
    }

    public void setRide(Set<Ride> ride) {
        this.ride = ride;
    }

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
                ", ride=" + ride +
                '}';
    }
}
