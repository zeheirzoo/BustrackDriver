package com.example.driver.model;

public class Preference {
//       "id": 1,
//                "costumer_id": 4,
//                "key": "theme",
//                "value": "dark"

    int id,costumer_id;
    String key,value;

    public Preference(int id, int costumer_id, String key, String value) {
        this.id = id;
        this.costumer_id = costumer_id;
        this.key = key;
        this.value = value;
    }

    public Preference(int costumer_id, String key, String value) {
        this.costumer_id = costumer_id;
        this.key = key;
        this.value = value;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Preference{" +
                "id=" + id +
                ", costumer_id=" + costumer_id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
