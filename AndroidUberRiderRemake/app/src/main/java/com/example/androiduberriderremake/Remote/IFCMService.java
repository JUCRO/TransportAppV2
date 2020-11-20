package com.example.androiduberriderremake.Remote;

import com.example.androiduberriderremake.Model.FCMResponse;
import com.example.androiduberriderremake.Model.FCMSendData;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAwYX12e4:APA91bEXs13hMll0cDhKGNIE1_NfueFuONZo9jRqfoasx43zIWSZmaGx9OtdRJbaMPVeTnUc8--f8UowsfKUNqqBusAToiDrfqLhEJ9l3eMLc9EJXWPvGK-2HRluG08EZ8PL3D9QcQnA"
    })
    @POST("fcm/send")
    Observable<FCMResponse> sendNotification(@Body FCMSendData body);
}
