package com.example.driver.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Line implements Serializable {

    private int id;
    private int linetype_id;
    private int a_b_id;
    private int b_a_id;
    private int identifier;
    private String colortype;
    private String city;
    private LineType linetype;
    private int prices;
    private List<Station>  station;
    private Station a_b_station;
    private Station b_a_station;

    public Line(int id, int linetype_id, int a_b_id, int b_a_id, int identifier, String colortype, String city, LineType linetype, int prices,
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

    public int getPrices() {
        return prices;
    }

    public void setPrices(int prices) {
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


    public List<Station> getTerminusStation() {
        List<Station>st=new ArrayList<>();

        for (Station s:station){
            if (s.isTerminus()){
                st.add(s);
            }
        }
        return st;
    }
//
//
//    public List<String> getStationsNames(String direction) {
//        List<String>strings=new ArrayList<>();
//        for (Station s:station){
//            strings.add((s.getName()));
//            if(!s.equals(station.get(station.size()-1))){
//                strings.add("");
//                strings.add("");
//                strings.add("");
//            }
//        }
//        if (direction=="b_a"){
//            Collections.reverse(strings);
//        }
//        return strings;
//    }
//
//
//
//
//    public List<Position> getStationsAndInerStationsPosition() {
//        List<Position>positions=new ArrayList<>();
//        for (Station s:station){
//           Position sPosition=new Position(s.getA_b_address(),s.getA_b_latitude(),s.getA_b_longitude());
//           positions.add(sPosition);
//          if(s.getSrcinterstation().size()>0){
//              for(IntermediaryPoint point : PointInOrder(s.getSrcinterstation().get(0).getIntermediary_point()) ){
//                  Position InterS_Position=new Position(point.getA_b_address(),point.getA_b_latitude(),point.getA_b_longitude());
//                  positions.add(InterS_Position);
//              }
//          }
//
//        }
//
//        return positions;
//    }
//    public List<Position> getStationsPosition() {
//        List<Position>positions=new ArrayList<>();
//        for (Station s:station){
//           Position sPosition=new Position(s.getA_b_address(),s.getA_b_latitude(),s.getA_b_longitude());
//           positions.add(sPosition);
//          }
//
//        return positions;
////    }
////    public List<Position> getPathPointList() {
////        List<Position>positions=new ArrayList<>();
////        for (Station s:station){
////            for (InterStation interStation:s.getSrcinterstation()){
////                try {
////                    JSONArray jsonArray=new JSONArray( interStation.getA_b_path());
////                    for (int i = 0; i < jsonArray.length(); i++) {
////                        double[] LatLongs= new Gson().fromJson(jsonArray.get(i).toString(),double[].class) ;
////                        for (int j = 0; j < LatLongs.length; j++) {
////                            Position sPosition=new Position(null,LatLongs[0],LatLongs[1]);
////                            positions.add(sPosition);
////                        }
////                    }
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////            }
////            ;
////          }
////
////        return positions;
////    }

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
                ", prices=" + prices +
                ", station=" + station +
                ", a_b_station=" + a_b_station +
                ", b_a_station=" + b_a_station +
                '}';
    }


//
//    public  List<IntermediaryPoint> PointInOrder(List<IntermediaryPoint> points){
////
//        Collections.sort(points, new Comparator<IntermediaryPoint>() {
//            @Override
//            public int compare(IntermediaryPoint o1, IntermediaryPoint o2) {
//                return o1.getOrder().compareTo(o2.getOrder());
//            }
//        });
//
//
//        return points;
//
//
//    }
}

