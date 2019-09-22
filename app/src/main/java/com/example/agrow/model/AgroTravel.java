package com.example.agrow.model;

import java.io.Serializable;

public class AgroTravel implements Serializable {
    private String id;
    private String title;
    private String location;
    private int image;

    public AgroTravel(String id, String title, String location, int image) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}