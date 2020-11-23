package com.example.androiduberriderremake.providers;

import com.example.androiduberriderremake.models.DriverRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class DriverRequestProvider {

    public DatabaseReference databaseReference;

    public DriverRequestProvider() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("DriverRequest");
    }

    public DriverRequest createRequest(String driverId) {
        try {
            DriverRequest driverRequest =  new DriverRequest();
            driverRequest.setId(UUID.randomUUID().toString());
            driverRequest.setDriverId(driverId);
            driverRequest.setStatus("Pendiente");
            driverRequest.setCreatedAt();
            databaseReference.child(driverRequest.getId()).setValue(driverRequest);
            return driverRequest;
        } catch (Exception error){
            return null;
        }
    }
}
