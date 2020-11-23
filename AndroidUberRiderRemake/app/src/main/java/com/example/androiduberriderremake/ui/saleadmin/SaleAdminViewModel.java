package com.example.androiduberriderremake.ui.saleadmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SaleAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SaleAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Promociones");
    }

    public LiveData<String> getText() {
        return mText;
    }
}