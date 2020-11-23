package com.example.androiduberriderremake.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DriverRequest implements Serializable {

    String id;
    String driverId;
    String status;
    String managerEmail;
    String observations;
    String created_at;
    String update_at;

    public DriverRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.created_at = simpleDateFormat.format(new Date());
    }

    public String getUpdatedAt() {return update_at;}

    public void setUpdateAt() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.update_at = simpleDateFormat.format(new Date());
    }
}
