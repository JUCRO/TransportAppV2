package com.example.androiduberriderremake.ui.registerdriveradmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androiduberriderremake.R;

public class RegisterDriverAdminFragment extends Fragment {

    private RegisterDriverAdminViewModel registerdriveradminViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registerdriveradminViewModel =
                ViewModelProviders.of(this).get(RegisterDriverAdminViewModel.class);
        View root = inflater.inflate(R.layout.fragment_register_driver_admin, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        registerdriveradminViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}