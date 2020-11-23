package com.example.androiduberremake;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androiduberremake.Utils.UserUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {
    EditText mTextInputEmail, mTextInputPassword;
    Button mButtonLogin, mButtonLoginCell;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    String userId, tokenUser;

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference(Common.DRIVER_INFO_REFERENCE);
        mAuth = FirebaseAuth.getInstance();

        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mButtonLogin = findViewById(R.id.btnLogin);
        mButtonLoginCell = findViewById(R.id.btnLoginCell);

        mDialog = new SpotsDialog.Builder().setContext(LoginActivity.this).setMessage("Espere un momento").build();

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        mButtonLoginCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SplashScreenActivity.class));
            }
        });
    }

    private void login() {
        String email = mTextInputEmail.getText().toString();
        final String password = mTextInputPassword.getText().toString();
        if(!email.isEmpty() && !password.isEmpty()) {
            if(password.length() >= 6) {
                mDialog.show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mDialog.dismiss();
                        if (task.isSuccessful()){
                            userId = mAuth.getCurrentUser().getUid();
                            Query q = databaseReference.child(userId);
                            q.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        //Update token
                                        FirebaseInstanceId.getInstance()
                                                .getInstanceId()
                                                .addOnFailureListener(e -> Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show())
                                                .addOnSuccessListener(instanceIdResult -> {
                                                    Log.d("TOKEN",instanceIdResult.getToken());
                                                    UserUtils.updateToken(LoginActivity.this,instanceIdResult.getToken());
                                                });
                                        startActivity(new Intent(LoginActivity.this, DriverHomeActivity.class));
                                    } else {
                                        FirebaseAuth.getInstance().signOut();
                                        Toast.makeText(LoginActivity.this, "No estas registrado como conductor", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(LoginActivity.this, "Que esperas! Envia tu solicitud ya", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, "El correo electronico o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "La contraseña debe tener más de 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "El correo o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // descomentar esto por error
        if (mAuth.getCurrentUser() != null){
            userId = mAuth.getCurrentUser().getUid();
            startActivity(new Intent(LoginActivity.this, DriverHomeActivity.class));
        }
    }
}