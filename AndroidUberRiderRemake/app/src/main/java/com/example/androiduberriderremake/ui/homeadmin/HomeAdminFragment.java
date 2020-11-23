package com.example.androiduberriderremake.ui.homeadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androiduberriderremake.R;
import com.example.androiduberriderremake.ListRequestAdapter;
import com.example.androiduberriderremake.models.ListRequests;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeAdminFragment extends Fragment {
    DatabaseReference databaseReference;
    List<ListRequests> elements;
    View root;

    private HomeAdminViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeAdminViewModel.class);
        root = inflater.inflate(R.layout.fragment_home_admin, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("DriverRequest");
        init();
        return root;
    }

    public void init(){
        elements = new ArrayList<>();
        Query q = databaseReference.orderByChild("status").equalTo("Pendiente");
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        elements.add(new ListRequests("Pendiente", dataSnapshot.child("createdAt").getValue().toString() , "Miguel Angel", dataSnapshot.child("id").getValue().toString()));
                    }
                    ListRequestAdapter listRequestAdapter = new ListRequestAdapter(elements, getContext());
                    RecyclerView recyclerView = root.findViewById(R.id.ListRequest);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(listRequestAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}