package com.example.agrow.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String title;
    private float unitcost;
    private float amount;
    private String description;
    private String sellerid;
    private String division;
    private String district;
    private String upazila;
    private String unionloc;
    private String availableDate;
    private String expiryDate;
    private int image;
    private double latitude,longitude;

    public Product(String id, String title, float unitcost, float amount, String description, String sellerid, String division, String district, String upazila, String unionloc, String availableDate, String expiryDate, int image, double latitude, double longitude) {
        this.id = id;
        this.title = title;
        this.unitcost = unitcost;
        this.amount = amount;
        this.description = description;
        this.sellerid = sellerid;
        this.division = division;
        this.district = district;
        this.upazila = upazila;
        this.unionloc = unionloc;
        this.availableDate = availableDate;
        this.expiryDate = expiryDate;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public float getUnitcost() {
        return unitcost;
    }

    public void setUnitcost(float unitcost) {
        this.unitcost = unitcost;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUpazila() {
        return upazila;
    }

    public void setUpazila(String upazila) {
        this.upazila = upazila;
    }

    public String getUnionloc() {
        return unionloc;
    }

    public void setUnionloc(String unionloc) {
        this.unionloc = unionloc;
    }

    public String getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
