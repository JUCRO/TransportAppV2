package com.example.androiduberriderremake.ui.saleadmin;

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

public class SaleAdminFragment extends Fragment {

    private SaleAdminViewModel saleadminViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        saleadminViewModel =
                ViewModelProviders.of(this).get(SaleAdminViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sale_admin, container, false);
        final TextView textView = root.findViewById(R.id.text_saleadmin);
        saleadminViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}