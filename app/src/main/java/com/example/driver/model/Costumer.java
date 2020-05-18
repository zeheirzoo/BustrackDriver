package com.example.driver.model;

import java.util.Set;

public class Costumer {

    int id;
    double credit;
    User user;
    Set<Preference> preference;
    Set<Favorites> favorites;
    Set<Position> positions;
    Set<Ride> ride;


    public Costumer(int id, double credit, User userl,
                    Set<Preference> preference, Set<Favorites> favorites,
                    Set<Position> positions, Set<Ride> ride) {
        this.id = id;
        this.credit = credit;
        this.user = userl;
        this.preference = preference;
        this.favorites = favorites;
        this.positions = positions;
        this.ride = ride;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public User getUser() {
        return user;
    }

    public void setUserl(User userl) {
        this.user = userl;
    }

    public Set<Preference> getPreference() {
        return preference;
    }

    public void setPreference(Set<Preference> preference) {
        this.preference = preference;
    }

    public Set<Favorites> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Favorites> favorites) {
        this.favorites = favorites;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public Set<Ride> getRide() {
        return ride;
    }

    public void setRide(Set<Ride> ride) {
        this.ride = ride;
    }

    @Override
    public String toString() {
        return "Costumer{" +
                "id=" + id +
                ", credit=" + credit +
                ", user=" + user +
                ", preference=" + preference +
                ", favorites=" + favorites +
                ", positions=" + positions +
                ", ride=" + ride +
                '}';
    }
}
