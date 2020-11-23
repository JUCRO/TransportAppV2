package com.example.androiduberriderremake.models;

public class DriverInformation {

    String bank;
    String countBank;
    String carColour;
    String licensePlate;
    String carModel;
    String status;

    public DriverInformation() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCountBank() {
        return countBank;
    }

    public void setCountBank(String countBank) {
        this.countBank = countBank;
    }

    public String getCarColour() {
        return carColour;
    }

    public void setCarColour(String carColour) {
        this.carColour = carColour;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
}
