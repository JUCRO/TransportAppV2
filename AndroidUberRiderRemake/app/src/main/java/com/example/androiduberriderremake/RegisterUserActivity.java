package com.example.androiduberriderremake;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androiduberriderremake.models.User;
import com.example.androiduberriderremake.providers.UserProvider;
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

import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;

public class RegisterUserActivity extends AppCompatActivity {

    private static final String TAG = "";
    Button btnRegister, btnBack;
    EditText txtEmail, txtPassword;
    AlertDialog mDialog;
    private DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    String tokenUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        //Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("DriverInfo");
        mAuth = FirebaseAuth.getInstance();
        //Campos
        btnRegister = findViewById(R.id.register);
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);
        //Accion Registro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    registerUser();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        btnBack = findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    Boolean registerUser() throws InterruptedException {
        final String email = txtEmail.getText().toString();
        final String password = txtPassword.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, "Debes ingresar un correo electronico", Toast.LENGTH_SHORT).show();
            return false;
        }
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if (!pattern.matcher(email).matches()) {
            Toast.makeText(this, "Correo electronico no valido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Debes ingresar una contraseña", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "La contraseña debe ser mayor a 6 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        mDialog = new SpotsDialog.Builder().setContext(RegisterUserActivity.this).setMessage("Registrando...").build();
        mDialog.show();
        Query q = databaseReference.orderByChild("email").equalTo(email);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot = snapshot;
                if (snapshot.exists()) {
                    Toast.makeText(RegisterUserActivity.this, "El correo ingresado ya se encuentra registrado.", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String id = mAuth.getCurrentUser().getUid();
                                UserProvider prov = new UserProvider();
                                User userOk = prov.createUser(email, password, id, tokenUser);
                                if (userOk != null) {
                                    String userId = userOk.getId();
                                    String email = userOk.getEmail();
                                    Log.d(TAG, "Usuario registrado exitosamente:success");
                                    Intent dataUpdateIn = new Intent(RegisterUserActivity.this, DataUpdate.class);
                                    dataUpdateIn.putExtra("userObj", userOk);
                                    startActivity(dataUpdateIn);
                                    Toast.makeText(RegisterUserActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Log.e(TAG, "Error al registrar:danger");
                                    Toast.makeText(RegisterUserActivity.this, "Ocurrio un error al crear el usuario", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.e(TAG, "Error al registrar:danger");
                                Toast.makeText(RegisterUserActivity.this, "Ocurrio un error al crear el usuario en DB", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        mDialog.hide();
        return true;
    }
}