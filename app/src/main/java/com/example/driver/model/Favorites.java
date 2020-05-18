package com.example.driver.model;

public class Favorites {


        private  int id;
        private  int costumer_id;
        private  int line_id;
        private  int src_id;
        private  int dest_id;
        private  int order;
        private  String name;
        private  Line line;
        private  Station srcstation;
        private  Station deststation;

    public Favorites(int id, int costumer_id, int line_id, int src_id, int dest_id,
                     int order, String name, Line line, Station srcstation,
                     Station deststation) {
        this.id = id;
        this.costumer_id = costumer_id;
        this.line_id = line_id;
        this.src_id = src_id;
        this.dest_id = dest_id;
        this.order = order;
        this.name = name;
        this.line = line;
        this.srcstation = srcstation;
        this.deststation = deststation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCostumer_id() {
        return costumer_id;
    }

    public void setCostumer_id(int costumer_id) {
        this.costumer_id = costumer_id;
    }

    public int getLine_id() {
        return line_id;
    }

    public void setLine_id(int line_id) {
        this.line_id = line_id;
    }

    public int getSrc_id() {
        return src_id;
    }

    public void setSrc_id(int src_id) {
        this.src_id = src_id;
    }

    public int getDest_id() {
        return dest_id;
    }

    public void setDest_id(int dest_id) {
        this.dest_id = dest_id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Station getSrcstation() {
        return srcstation;
    }

    public void setSrcstation(Station srcstation) {
        this.srcstation = srcstation;
    }

    public Station getDeststation() {
        return deststation;
    }

    public void setDeststation(Station deststation) {
        this.deststation = deststation;
    }

    @Override
    public String toString() {
        return "Favorites{" +
                "id=" + id +
                ", costumer_id=" + costumer_id +
                ", line_id=" + line_id +
                ", src_id=" + src_id +
                ", dest_id=" + dest_id +
                ", order=" + order +
                ", name='" + name + '\'' +
                ", line=" + line +
                ", srcstation=" + srcstation +
                ", deststation=" + deststation +
                '}';
    }
}
