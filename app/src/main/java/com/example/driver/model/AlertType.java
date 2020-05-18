package com.example.driver.model;

class AlertType {

    /**
     *        "id": 1,
     *             "name": "Abuse",
     *             "description": "An abuse (smooking, ...)",
     *             "level": 3
     */


    private int id;
    private String name;
    private String description;
    private int level;

    public AlertType(int id, String name, String description, int level) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
    public String toString() {
        return "AlertType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", level=" + level +
                '}';
    }
}
