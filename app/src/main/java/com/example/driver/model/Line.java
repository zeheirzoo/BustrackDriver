package com.example.driver.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Line {

    private int id;
    private int linetype_id;
    private int a_b_id;
    private int b_a_id;
    private int identifier;
    private String colortype;
    private String city;
    private LineType linetype;
    private String[] prices;
    private List<Station>  station;
    private Station a_b_station;
    private Station b_a_station;

    public Line(int id, int linetype_id, int a_b_id, int b_a_id, int identifier, String colortype, String city, LineType linetype, String[] prices,
                List<Station> station, Station a_b_station, Station b_a_station) {
        this.id = id;
        this.linetype_id = linetype_id;
        this.a_b_id = a_b_id;
        this.b_a_id = b_a_id;
        this.identifier = identifier;
        this.colortype = colortype;
        this.city = city;
        this.linetype = linetype;
        this.prices = prices;
        this.station = station;
        this.a_b_station = a_b_station;
        this.b_a_station = b_a_station;
    }

    public LineType getLinetype() {
        return linetype;
    }

    public void setLinetype(LineType linetype) {
        this.linetype = linetype;
    }

    public String[] getPrices() {
        return prices;
    }

    public void setPrices(String[] prices) {
        this.prices = prices;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLinetype_id() {
        return linetype_id;
    }

    public void setLinetype_id(int linetype_id) {
        this.linetype_id = linetype_id;
    }

    public int getA_b_id() {
        return a_b_id;
    }

    public void setA_b_id(int a_b_id) {
        this.a_b_id = a_b_id;
    }

    public int getB_a_id() {
        return b_a_id;
    }

    public void setB_a_id(int b_a_id) {
        this.b_a_id = b_a_id;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getColortype() {
        return colortype;
    }

    public void setColortype(String colortype) {
        this.colortype = colortype;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Station> getStation() {
        return station;
    }

    public void setStation(List<Station> station) {
        this.station = station;
    }

    public Station getA_b_station() {
        return a_b_station;
    }

    public void setA_b_station(Station a_b_station) {
        this.a_b_station = a_b_station;
    }

    public Station getB_a_station() {
        return b_a_station;
    }

    public void setB_a_station(Station b_a_station) {
        this.b_a_station = b_a_station;
    }



    public List<String> getStationsNams() {
        List<String>strings=new ArrayList<>();
        for (Station s:station){
            strings.add((s.getName()));
            if(s.getId()<station.size()){
                strings.add("");
                strings.add("");
                strings.add("");
            }

        }
        return strings;
    }

    public List<Position> getStationsAndInerStationsPosition() {
        List<Position>positions=new ArrayList<>();
        for (Station s:station){
           Position sPosition=new Position(s.getA_b_address(),s.getA_b_latitude(),s.getA_b_longitude());
           positions.add(sPosition);
          if(s.getSrcinterstation().size()>0){
              for(IntermediaryPoint point : s.getSrcinterstation().get(0).getIntermediary_point()){

                  Position InterS_Position=new Position(point.getA_b_address(),point.getA_b_latitude(),point.getA_b_longitude());
                  positions.add(sPosition);
              }
          }

        }
        return positions;
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + id +
                ", linetype_id=" + linetype_id +
                ", a_b_id=" + a_b_id +
                ", b_a_id=" + b_a_id +
                ", identifier=" + identifier +
                ", colortype='" + colortype + '\'' +
                ", city='" + city + '\'' +
                ", linetype=" + linetype +
                ", prices=" + Arrays.toString(prices) +
                ", station=" + station +
                ", a_b_station=" + a_b_station +
                ", b_a_station=" + b_a_station +
                '}';
    }
}

