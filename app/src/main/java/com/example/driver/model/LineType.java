package com.example.driver.model;

import java.io.Serializable;
import java.util.Objects;

public class LineType implements Serializable {


    private int id;
    private String name;

    public LineType(int id, String name) {
        this.id = id;
        this.name = name;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineType LineType = (LineType) o;
        return id == LineType.id &&
                Objects.equals(name, LineType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,  name);
    }

    @Override
    public String toString() {
        return "LineType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
