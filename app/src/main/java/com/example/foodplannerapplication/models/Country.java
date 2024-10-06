package com.example.foodplannerapplication.models;


public class Country {

    private final String strArea;

    private String strCode;

    public Country(String strArea) {
        this.strArea = strArea;
    }

    public String getStrArea() {
        return strArea;
    }

    public String getStrCode() {
        return strCode;
    }

    public void setStrCode(String strCode) {
        this.strCode = strCode;
    }
}