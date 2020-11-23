package com.example.androiduberriderremake.models;

import java.util.Date;

public class UserRoles {

    String rol;
    Boolean current;
    Date created_at;

    public UserRoles() {
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt() {this.created_at = new Date();}
}
