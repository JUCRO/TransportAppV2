package com.example.androiduberremake;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GlobalClass extends Application {
    private String token;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void sendNotificationTopic(String topic, String titel, String body, JSONObject aditional){
        RequestQueue myRequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            JSONObject notification = new JSONObject();
            notification.put("titel", titel);
            notification.put("body", body);
            json.put("to", "/topics/"+topic);
            json.put("data", notification);
            String url = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url, json,null, null){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAA99TgDs4:APA91bHo2ENR7HTU5tiDGYx3_W2Asay3KOYHm-_L1fowChr5ULpTe79S1HmZG-h7X4QVCXxM6y2UzTk0wnBoKPVPxTs9ncPwbNj1HSTpfl7oeUMqyqB3pZ5YfxLJIJWiOxlqS5-hUv9f");
                    return header;
                }
            };
            myRequest.add(request);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void sendNotificationSpecific(String token){
        RequestQueue myRequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();
        try {
            JSONObject notification = new JSONObject();
            notification.put("titulo", "soy el titulo");
            notification.put("cuerpo", "soy el cuerpo");
            json.put("to", token);
            json.put("data", notification);
            String url = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url, json,null, null){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAA99TgDs4:APA91bHo2ENR7HTU5tiDGYx3_W2Asay3KOYHm-_L1fowChr5ULpTe79S1HmZG-h7X4QVCXxM6y2UzTk0wnBoKPVPxTs9ncPwbNj1HSTpfl7oeUMqyqB3pZ5YfxLJIJWiOxlqS5-hUv9f");
                    return header;
                }
            };
            myRequest.add(request);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
