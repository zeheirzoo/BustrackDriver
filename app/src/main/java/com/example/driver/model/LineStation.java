package com.example.driver.model;

import java.io.Serializable;

public class LineStation  implements Serializable {
//     {
//                    "line_id": 1,
//                    "station_id": 2,
//                    "order": 6,
//                    "is_terminus": 1
//                }

    int line_id;
    int station_id;
    int order;
    int is_terminus;

    public LineStation(int line_id, int station_id, int order, int is_terminus) {
        this.line_id = line_id;
        this.station_id = station_id;
        this.order = order;
        this.is_terminus = is_terminus;
    }

    public int getLine_id() {
        return line_id;
    }

    public void setLine_id(int line_id) {
        this.line_id = line_id;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getIs_terminus() {
        return is_terminus;
    }

    public void setIs_terminus(int is_terminus) {
        this.is_terminus = is_terminus;
    }
}
