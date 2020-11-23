package com.example.androiduberriderremake.providers;

import com.example.androiduberriderremake.models.Promotion;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class PromotionProvider {

    public DatabaseReference databaseReference;

    public PromotionProvider() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Promotion");
    }

    public boolean createPromotion(Promotion promotion) {
        try {
            String Id = UUID.randomUUID().toString();
            promotion.setId(Id);
            databaseReference.child(Id).setValue(promotion);
            return true;
        } catch (Exception error){
            return false;
        }
    }
}

