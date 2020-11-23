package com.example.androiduberriderremake.models;

public class UserInformation {

    String firstName;
    String lastName;
    String identification;
    String phoneNumber;
    String birthdate;

    public UserInformation(String name, String lastName, String identification, String phone, String birthdate) {
        this.firstName = name;
        this.lastName = lastName;
        this.identification = identification;
        this.phoneNumber = phone;
        this.birthdate = birthdate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
