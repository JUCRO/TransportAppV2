package com.example.androiduberremake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    private CircleImageView riverAvatar;
    private TextView riverName;
    private RecyclerView recyclerView;
    private EditText messageSend;
    private Button btnSendMessage;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private AdapterMessage adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent messageTrip = getIntent();
        String idTrip = (String) messageTrip.getSerializableExtra("idTrip");
        riverAvatar = (CircleImageView) findViewById(R.id.riverAvatar);
        riverName = (TextView) findViewById(R.id.riverName);
        recyclerView = (RecyclerView) findViewById(R.id.rvMessage);
        messageSend = (EditText) findViewById(R.id.inputMessage);
        btnSendMessage = (Button) findViewById(R.id.btnSendMessage);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Common.Trip).child(idTrip).child(Common.MESSAGES_TRIP);

        adapter = new AdapterMessage(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(adapter);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.push().setValue(new Message(messageSend.getText().toString(), riverName.getText().toString()));
                messageSend.setText("");
            }
        });
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message m = dataSnapshot.getValue(Message.class);
                adapter.addMessages(m);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setScrollBar(){
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }
}