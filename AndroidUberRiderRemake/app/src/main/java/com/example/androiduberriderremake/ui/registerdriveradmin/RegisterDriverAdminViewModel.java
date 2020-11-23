package com.example.androiduberriderremake.ui.registerdriveradmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterDriverAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RegisterDriverAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Registro de conductores");
    }

    public LiveData<String> getText() {
        return mText;
    }
}