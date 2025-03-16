package com.example.easypark.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.easypark.R;

public class MetodosDePago extends AppCompatActivity {

    Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_metodos_de_pago);

        btnVolver = findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(MetodosDePago.this,Pagos.class);
                startActivity(intento);
            }
        });


    }

    public void opcionVisa(View view){
        Intent intent = new Intent(MetodosDePago.this, PagoAceptado.class);
        startActivity(intent);
    }

    public void opcionMastercard(View view){
        Intent intent = new Intent(MetodosDePago.this, PagoAceptado.class);
        startActivity(intent);
    }

    public void opcionPse(View view){
        Intent intent = new Intent(MetodosDePago.this, PagoAceptado.class);
        startActivity(intent);
    }

    public void opcionPagoEfectivo(View view){
        Intent intent = new Intent(MetodosDePago.this, PagoAceptado.class);
        startActivity(intent);
    }
}