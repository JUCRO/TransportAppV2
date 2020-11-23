package com.example.androiduberriderremake.ui.homeadmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Solicitudes");
    }

    public LiveData<String> getText() {
        return mText;
    }
}