package com.example.androiduberriderremake.models;

public class Promotion {

    String id;
    String name;
    String discount;
    String initialDate;
    String finalDate;

    public Promotion(String name, String discount, String initialDate, String finalDate) {
        this.name = name;
        this.discount = discount;
        this.initialDate = initialDate;
        this.finalDate = finalDate;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }
}
