package com.example.driver.model;

import java.util.Date;

public class Vehicle {


    /**?
     *  {
     *         "id": 1,
     *         "vehicle_owner_id": 2,
     *         "vehicle_type_id": 1,
     *         "matricule": "00000-000-00",
     *         "branch": "Sonacom",
     *         "registernumbre": "123456789",
     *         "seatmaxcount": 45,
     *         "vehicle_type": {
     *             "id": 1,
     *             "name": "bus"
     *         },
     *         "vehicle_position": [
     *             {
     *                 "id": 1,
     *                 "latitude": 36.35813999999999879264578339643776416778564453125,
     *                 "longitude": 6.64280000000000026005864128819666802883148193359375,
     *                 "address": null,
     *                 "created_at": "2020-05-15 18:59:11.000000"
     *             }
     *         ]
     *     }
     */
    private int id;
    private int vehicle_owner_id;
    private int vehicle_type_id;
    private int seatmaxcount;
    private String matricule;
    private String branch;
    private String registernumbre;
    private Position vehicle_position;
    private VehicleType vehicle_type;


    public Vehicle(int id, int vehicle_owner_id, int vehicle_type_id,
                   int seatmaxcount, String matricule, String branch,
                   String registernumbre, Position vehicle_position,
                   VehicleType vehicle_type) {
        this.id = id;
        this.vehicle_owner_id = vehicle_owner_id;
        this.vehicle_type_id = vehicle_type_id;
        this.seatmaxcount = seatmaxcount;
        this.matricule = matricule;
        this.branch = branch;
        this.registernumbre = registernumbre;
        this.vehicle_position = vehicle_position;
        this.vehicle_type = vehicle_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehicle_owner_id() {
        return vehicle_owner_id;
    }

    public void setVehicle_owner_id(int vehicle_owner_id) {
        this.vehicle_owner_id = vehicle_owner_id;
    }

    public int getVehicle_type_id() {
        return vehicle_type_id;
    }

    public void setVehicle_type_id(int vehicle_type_id) {
        this.vehicle_type_id = vehicle_type_id;
    }

    public int getSeatmaxcount() {
        return seatmaxcount;
    }

    public void setSeatmaxcount(int seatmaxcount) {
        this.seatmaxcount = seatmaxcount;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRegisternumbre() {
        return registernumbre;
    }

    public void setRegisternumbre(String registernumbre) {
        this.registernumbre = registernumbre;
    }

    public Position getVehicle_position() {
        return vehicle_position;
    }

    public void setVehicle_position(Position vehicle_position) {
        this.vehicle_position = vehicle_position;
    }

    public VehicleType getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(VehicleType vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", vehicle_owner_id=" + vehicle_owner_id +
                ", vehicle_type_id=" + vehicle_type_id +
                ", seatmaxcount=" + seatmaxcount +
                ", matricule='" + matricule + '\'' +
                ", branch='" + branch + '\'' +
                ", registernumbre='" + registernumbre + '\'' +
                ", vehicle_position=" + vehicle_position +
                ", vehicle_type=" + vehicle_type +
                '}';
    }
}
