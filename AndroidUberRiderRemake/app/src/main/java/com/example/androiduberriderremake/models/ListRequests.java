package com.example.androiduberriderremake.models;

public class ListRequests {
    public String status;
    public String dateRequest;
    public String nameDriver;
    public String idRequest;

    public ListRequests(String status, String dateRequest, String nameDriver, String idRequest) {
        this.status = status;
        this.dateRequest = dateRequest;
        this.nameDriver = nameDriver;
        this.idRequest = idRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(String dateRequest) {
        this.dateRequest = dateRequest;
    }

    public String getNameDriver() {
        return nameDriver;
    }

    public void setNameDriver(String nameDriver) {
        this.nameDriver = nameDriver;
    }

    public String getIdRequest() {return idRequest;}

    public void setIdRequest(String idRequest) {this.idRequest = idRequest;}
}
