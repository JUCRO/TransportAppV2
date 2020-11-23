package com.example.androiduberriderremake;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DetailRequestActivity extends AppCompatActivity {

    FirebaseStorage firebaseStorage;
    DatabaseReference databaseReferenceRequest, databaseReferenceUsers;
    StorageReference refLicencia, refTenico, refSOAT, refPropiedad, refFrontal, refTrasera, refDerecha, refIzquierda;
    FirebaseAuth mAuth;
    ImageView ivLicencia, ivTecnico, ivSOAT, ivPropiedad, ivFrontal, ivTrasera, ivDerecha, ivIzquierda;
    String idRequest;
    TextView cuenta, banco, placa, color, modelo, nombreConductor;
    EditText observaciones;
    Button exit, accept, refuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request);
        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        databaseReferenceRequest = FirebaseDatabase.getInstance().getReference().child("DriverRequest");
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child("Riders");
        ivLicencia = findViewById(R.id.imLicencia);
        ivTecnico = findViewById(R.id.imTecnico);
        ivSOAT = findViewById(R.id.imSOAT);
        ivPropiedad = findViewById(R.id.imPropiedad);
        ivFrontal = findViewById(R.id.imFrontal);
        ivTrasera = findViewById(R.id.imTrasera);
        ivDerecha = findViewById(R.id.imDerecha);
        ivIzquierda = findViewById(R.id.imIzquierda);
        cuenta = findViewById(R.id.reqNumeroCuenta);
        banco = findViewById(R.id.reqBanco);
        placa = findViewById(R.id.reqPlaca);
        color = findViewById(R.id.reqColor);
        modelo = findViewById(R.id.reqModelo);
        nombreConductor = findViewById(R.id.requestNameDriver);
        observaciones = findViewById(R.id.txtObservations);
        Bundle bundle = this.getIntent().getExtras();
        idRequest = bundle.getString("idRequest");
        getData();
        exit = findViewById(R.id.exitDetailRequest);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailRequestActivity.this, MenuAdminActivity.class));
            }
        });
        accept = findViewById(R.id.btnAccept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerRequest("Aceptado");
            }
        });
        refuse = findViewById(R.id.btnRefuse);
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (observaciones.getText().toString().isEmpty()){
                    Toast.makeText(DetailRequestActivity.this, "Debes colocar una observación", Toast.LENGTH_SHORT).show();
                }else{
                    answerRequest("Rechazado");
                }
            }
        });
    }

    public void answerRequest(String answer){
        Query q = databaseReferenceRequest.child(idRequest);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String driverID = snapshot.child("driverId").getValue().toString();
                    Map<String, Object> driverRequest =  new HashMap<>();
                    driverRequest.put("status", answer);
                    driverRequest.put("managerEmail", mAuth.getCurrentUser().getEmail());
                    driverRequest.put("observations", observaciones.getText().toString());
                    driverRequest.put("updateAt", simpleDateFormat.format(new Date()));
                    databaseReferenceRequest.child(idRequest).updateChildren(driverRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Query q2 = databaseReferenceUsers.child(driverID);
                            q2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        Map<String, Object> driver =  new HashMap<>();
                                        driver.put("status", answer);
                                        databaseReferenceUsers.child(driverID).child("DriverInformation").updateChildren(driver).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(DetailRequestActivity.this, "Respuesta guardada exitosamente", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(DetailRequestActivity.this, MenuAdminActivity.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(DetailRequestActivity.this, "hubo un error al guardar la respuesta", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void getData(){
        Query q = databaseReferenceRequest.child(idRequest);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String driverID = snapshot.child("driverId").getValue().toString();
                    Query q2 = databaseReferenceUsers.child(driverID);
                    q2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                cuenta.setText(snapshot.child("DriverInformation").child("countBank").getValue().toString());
                                banco.setText(snapshot.child("DriverInformation").child("bank").getValue().toString());
                                placa.setText(snapshot.child("DriverInformation").child("licensePlate").getValue().toString());
                                color.setText(snapshot.child("DriverInformation").child("carColour").getValue().toString());
                                modelo.setText(snapshot.child("DriverInformation").child("carModel").getValue().toString());
                                nombreConductor.setText(snapshot.child("UserInformation").child("name").getValue().toString() + " " + snapshot.child("UserInformation").child("lastName").getValue().toString());
                                downloadImages(driverID);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void downloadImages(String driverID){
        refLicencia = firebaseStorage.getReference().child("Uploads").child(driverID).child("licencia");
        refLicencia.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(DetailRequestActivity.this).load(uri).fitCenter().into(ivLicencia);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailRequestActivity.this, "No se pudo descargar la imagen de licencia", Toast.LENGTH_SHORT).show();
            }
        });
        refTenico = firebaseStorage.getReference().child("Uploads").child(driverID).child("tecnico");
        refTenico.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(DetailRequestActivity.this).load(uri).fitCenter().into(ivTecnico);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailRequestActivity.this, "No se pudo descargar la imagen técnico mecánica", Toast.LENGTH_SHORT).show();
            }
        });
        refSOAT = firebaseStorage.getReference().child("Uploads").child(driverID).child("SOAT");
        refSOAT.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(DetailRequestActivity.this).load(uri).fitCenter().into(ivSOAT);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailRequestActivity.this, "No se pudo descargar la imagen del SOAT", Toast.LENGTH_SHORT).show();
            }
        });
        refPropiedad = firebaseStorage.getReference().child("Uploads").child(driverID).child("propiedad");
        refPropiedad.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(DetailRequestActivity.this).load(uri).fitCenter().into(ivPropiedad);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailRequestActivity.this, "No se pudo descargar la imagen de propiedad", Toast.LENGTH_SHORT).show();
            }
        });
        refFrontal = firebaseStorage.getReference().child("Uploads").child(driverID).child("frontal");
        refFrontal.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(DetailRequestActivity.this).load(uri).fitCenter().into(ivFrontal);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailRequestActivity.this, "No se pudo descargar la imagen frontal del auto", Toast.LENGTH_SHORT).show();
            }
        });
        refTrasera = firebaseStorage.getReference().child("Uploads").child(driverID).child("trasera");
        refTrasera.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(DetailRequestActivity.this).load(uri).fitCenter().into(ivTrasera);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailRequestActivity.this, "No se pudo descargar la imagen trasera del auto", Toast.LENGTH_SHORT).show();
            }
        });
        refIzquierda = firebaseStorage.getReference().child("Uploads").child(driverID).child("izquierda");
        refIzquierda.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(DetailRequestActivity.this).load(uri).fitCenter().into(ivIzquierda);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailRequestActivity.this, "No se pudo descargar la imagen del costado izquierdo del auto", Toast.LENGTH_SHORT).show();
            }
        });
        refDerecha = firebaseStorage.getReference().child("Uploads").child(driverID).child("derecha");
        refDerecha.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(DetailRequestActivity.this).load(uri).fitCenter().into(ivDerecha);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DetailRequestActivity.this, "No se pudo descargar la imagen del costado derecho del auto", Toast.LENGTH_SHORT).show();
            }
        });
    }
}