package com.example.androiduberriderremake;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androiduberriderremake.models.Promotion;
import com.example.androiduberriderremake.providers.PromotionProvider;

import dmax.dialog.SpotsDialog;

public class AddPromotion extends AppCompatActivity {

    Button btnAddPromotion, btnExitPromo;
    TextView txtNombrePromo, txtDescuento, txtFechaInicial, txtFechaFinal;
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promotion);
        btnAddPromotion = (Button)findViewById(R.id.btnAddPromo);
        txtNombrePromo = (TextView)findViewById(R.id.txtNombrePromo);
        txtDescuento = (TextView)findViewById(R.id.txtDescuento);
        txtFechaInicial = (TextView)findViewById(R.id.txtFechaInicial);
        txtFechaFinal = (TextView)findViewById(R.id.txtFechaFinal);
        btnExitPromo = (Button) findViewById(R.id.btnExitPromo);

        btnExitPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnAddPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    boolean result = AddPromo();
                    if(result){
                        Intent menuAdmin = new Intent(AddPromotion.this, MenuAdminActivity.class);
                        mDialog.dismiss();
                        startActivity(menuAdmin);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean AddPromo() throws InterruptedException {

        if (txtNombrePromo.getText().toString().isEmpty()) {
            Toast.makeText(this, "El campo de nombre promocion es requerido!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtDescuento.getText().toString().isEmpty()) {
            Toast.makeText(this, "El campo descuento es requerido!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtFechaInicial.getText().toString().isEmpty()) {
            Toast.makeText(this, "Es necesario agregar una fecha inicial de la promocion!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtFechaFinal.getText().toString().isEmpty()) {
            Toast.makeText(this, "Es necesario agregar una fecha final de la promocion!", Toast.LENGTH_SHORT).show();
            return false;
        }
        mDialog = new SpotsDialog.Builder().setContext(AddPromotion.this).setMessage("Registrando...").build();
        mDialog.show();
        PromotionProvider prov = new PromotionProvider();
        Promotion promObject = new Promotion(
                txtNombrePromo.getText().toString(),
                txtDescuento.getText().toString(),
                txtFechaInicial.getText().toString(),
                txtFechaFinal.getText().toString()
        );
        boolean result = prov.createPromotion(promObject);
        if(!result){
            Toast.makeText(this, "Ocurrio un error al intentar crear la promocion!", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(this, "Se creo correctamente la promocion!", Toast.LENGTH_SHORT).show();
        return true;
    }
}