package com.example.androiduberriderremake.Utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.androiduberriderremake.Common.Common;
import com.example.androiduberriderremake.Model.DriverGeoModel;
import com.example.androiduberriderremake.Model.EventBus.SelectePlaceEvent;
import com.example.androiduberriderremake.Model.FCMResponse;
import com.example.androiduberriderremake.Model.FCMSendData;
import com.example.androiduberriderremake.Model.TokenModel;
import com.example.androiduberriderremake.R;
import com.example.androiduberriderremake.Remote.IFCMService;
import com.example.androiduberriderremake.Remote.RetrofitFCMClient;
import com.example.androiduberriderremake.RequestDriverActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserUtils {
    public static void updateUser(View view, Map<String, Object> updateData) {
        FirebaseDatabase.getInstance()
                .getReference(Common.RIDER_INFO_REFENCE)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .updateChildren(updateData)
                .addOnFailureListener(e -> Snackbar.make(view,e.getMessage(),Snackbar.LENGTH_SHORT).show())
                .addOnSuccessListener(aVoid -> Snackbar.make(view,"¡Actualización de información completa!",Snackbar.LENGTH_SHORT).show());
    }

    public static void updateToken(Context context, String token) {
        Map<String, Object> driverInfo =  new HashMap<>();
        driverInfo.put("token", token);
        FirebaseDatabase.getInstance()
                .getReference(Common.RIDER_INFO_REFENCE)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .updateChildren(driverInfo)
                .addOnFailureListener(e -> Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show()).addOnSuccessListener(aVoid -> {

        });
    }

    public static void sendRequestToDriver(Context context, RelativeLayout main_layout, DriverGeoModel foundDriver, SelectePlaceEvent selectePlaceEvent) {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        IFCMService ifcmService = RetrofitFCMClient.getInstance().create(IFCMService.class);

        //Get token
        FirebaseDatabase
                .getInstance()
                .getReference(Common.DRIVER_INFO_REFERENCE)
                .child(foundDriver.getKey())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            TokenModel tokenModel = dataSnapshot.getValue(TokenModel.class);

                            Map<String,String> notificationData = new HashMap<>();
                            notificationData.put(Common.NOTI_TITLE,Common.REQUEST_DRIVER_TITLE);
                            notificationData.put(Common.NOTI_CONTENT,"This message respersent for request driver action");
                            notificationData.put(Common.RIDER_KEY,FirebaseAuth.getInstance().getCurrentUser().getUid());

                            notificationData.put(Common.RIDER_PICKUP_LOCATION_STRING,selectePlaceEvent.getOriginString());
                            notificationData.put(Common.RIDER_PICKUP_LOCATION,new StringBuilder("")
                            .append(selectePlaceEvent.getOrigin().latitude)
                            .append(",")
                            .append(selectePlaceEvent.getOrigin().longitude)
                            .toString());

                            notificationData.put(Common.RIDER_DESTINATION_STRING,selectePlaceEvent.getAddress());
                            notificationData.put(Common.RIDER_DESTINATION,new StringBuilder("")
                                    .append(selectePlaceEvent.getDestination().latitude)
                                    .append(",")
                                    .append(selectePlaceEvent.getDestination().longitude)
                                    .toString());

                            FCMSendData fcmSendData = new FCMSendData(tokenModel.getToken(),notificationData);

                            compositeDisposable.add(ifcmService.sendNotification(fcmSendData)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(fcmResponse -> {
                                if(fcmResponse.getSuccess() == 0)
                                {
                                    compositeDisposable.clear();
                                    Snackbar.make(main_layout,context.getString(R.string.request_driver_failed),Snackbar.LENGTH_LONG).show();
                                }

                            }, throwable -> {
                                compositeDisposable.clear();
                                Snackbar.make(main_layout,throwable.getMessage(),Snackbar.LENGTH_LONG).show();
                            }));
                        }
                        else
                        {
                            Snackbar.make(main_layout,context.getString(R.string.token_not_found),Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Snackbar.make(main_layout,databaseError.getMessage(),Snackbar.LENGTH_LONG).show();

                    }
                });
    }
}
