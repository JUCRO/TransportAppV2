package com.example.androiduberriderremake.Model.EventBus;

public class ShowNotificationFinishTrip {
    private String tripkey;

    public ShowNotificationFinishTrip(String tripkey) {
        this.tripkey = tripkey;
    }

    public String getTripkey() {
        return tripkey;
    }

    public void setTripkey(String tripkey) {
        this.tripkey = tripkey;
    }
}
