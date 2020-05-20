package com.example.driver.model;

import java.util.Date;

public class User {
//
//     {
//        "id": 2,
//        "sold": 100,
//        "user": {
//            "id": 3,
//            "username": "zaki_dahaba",
//            "firstname": "Zaki",
//            "lastname": "Dahaba",
//            "email": "zaki-dehaba@gmail.com",
//            "role": "costumer",
//            "created_at": "2020-05-07T22:17:07.000000Z",
//            "updated_at": "2020-05-07T22:17:07.000000Z"
//        },
//        "preference": [],
//        "favorites": [],
//        "ride": []

    int id;
    String username,firstname,lastname,email,password,role,userable_type;
    Date created_at,updated_at;

    public User(int id, String username, String firstname, String lastname, String email, String password, String role, Date created_at, Date updated_at) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public User(int id, String username, String firstname, String lastname, String email, String password, String role, String userable_type, Date created_at, Date updated_at) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.userable_type = userable_type;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public User(String email, String pass) {
        this.email = email;
        this.password = pass;
    }



    public String getUserable_type() {
        return userable_type;
    }

    public void setUserable_type(String userable_type) {
        this.userable_type = userable_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", userable_type='" + userable_type + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
