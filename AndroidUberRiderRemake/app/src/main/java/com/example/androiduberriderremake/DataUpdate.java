package com.example.androiduberriderremake;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androiduberriderremake.Common.Common;
import com.example.androiduberriderremake.models.User;
import com.example.androiduberriderremake.models.UserInformation;
import com.example.androiduberriderremake.providers.UserProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;

public class DataUpdate extends AppCompatActivity {

    Button btnUpdateInformation, btnExit;
    TextView getTxtEmailUser, txtNombre, txtApellido, txtIdentifiacion, txtTelefono, txtFechaNacimiento;
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_update);
        getTxtEmailUser = (TextView) findViewById(R.id.getEmail);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtApellido = (TextView) findViewById(R.id.txtApellido);
        txtIdentifiacion = (TextView) findViewById(R.id.txtNumeroIdentificacion);
        txtTelefono = (TextView) findViewById(R.id.txtCelular);
        txtFechaNacimiento = (TextView) findViewById(R.id.txtFechaNacimiento);
        btnUpdateInformation = (Button) findViewById(R.id.btnUpdate);
        Intent dataUpdateIn = getIntent();
        User userGet = (User) dataUpdateIn.getSerializableExtra("userObj");
        if (userGet != null) {
            getTxtEmailUser.setText(userGet.getEmail().toString());
        }
        btnExit = findViewById(R.id.exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataUpdate.this,HomeActivity.class));
            }
        });

        FirebaseDatabase.getInstance().getReference(Common.RIDER_INFO_REFENCE)
        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.hasChild("birthdate")){
                        txtFechaNacimiento.setText(dataSnapshot.child("birthdate").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("firstName")){
                        txtNombre.setText(dataSnapshot.child("firstName").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("lastName")){
                        txtApellido.setText(dataSnapshot.child("lastName").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("phoneNumber")){
                        txtTelefono.setText(dataSnapshot.child("phoneNumber").getValue().toString());
                    }
                    if (dataSnapshot.hasChild("identification")){
                        txtIdentifiacion.setText(dataSnapshot.child("identification").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnUpdateInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent dataUpdateIn = getIntent();
                    User userGet = (User) dataUpdateIn.getSerializableExtra("userObj");
                    boolean updateOK = UpdateInformation(userGet);
                    if(updateOK){
                        Intent menuActIn = new Intent(DataUpdate.this, HomeActivity.class);
                        mDialog.dismiss();
                        startActivity(menuActIn);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean UpdateInformation(User userGet) throws InterruptedException {

        if (txtNombre.getText().toString().isEmpty()) {
            Toast.makeText(this, "El campo de nombre es requerido!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtApellido.getText().toString().isEmpty()) {
            Toast.makeText(this, "El campo de apellido es requerido!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtIdentifiacion.getText().toString().isEmpty()) {
            Toast.makeText(this, "El numero de identifiacion es requerido!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtTelefono.getText().toString().isEmpty()) {
            Toast.makeText(this, "El campo de telefono es requerido!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtFechaNacimiento.getText().toString().isEmpty()) {
            Toast.makeText(this, "La fecha de nacimiento es requerida!", Toast.LENGTH_SHORT).show();
            return false;
        }
        mDialog = new SpotsDialog.Builder().setContext(DataUpdate.this).setMessage("Registrando...").build();
        mDialog.show();
        UserProvider prov = new UserProvider();
        UserInformation userInformation = new UserInformation(
                txtNombre.getText().toString(),
                txtApellido.getText().toString(),
                txtIdentifiacion.getText().toString(),
                txtTelefono.getText().toString(),
                txtFechaNacimiento.getText().toString()
                );
        boolean result = prov.registerUserInformation(userInformation, FirebaseAuth.getInstance().getCurrentUser().getUid());
        if(!result){
            Toast.makeText(this, "Ocurrio un error al intentar actualizar la informacion del usuario!", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(this, "Se actualizo correctamente la informacion del usuario!", Toast.LENGTH_SHORT).show();
        return true;
    }
}