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

public class HomeAdmin extends AppCompatActivity {

    Button btnSalirAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_admin);

        btnSalirAdmin = findViewById(R.id.btnSalirAdmin);

        btnSalirAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeAdmin.this, Principal.class);
                startActivity(intent);
            }
        });

    }

    public void irAjustarTarifas(View view){
        Intent intent = new Intent(HomeAdmin.this, PantallaCarga_AjustarTarifas.class);
        startActivity(intent);
    }

    public void irRegistrarIncidencias(View view){
        Intent intent = new Intent(HomeAdmin.this, PantallaCarga_RegistrarIncidencias.class);
        startActivity(intent);

    }

    public void irHistorialIncidencias(View view){
        Intent intent = new Intent(HomeAdmin.this, PantallaCarga_HistorialIncidencias.class);
        startActivity(intent);

    }
    public void irGenerarReportes(View view){
        Intent intent = new Intent(HomeAdmin.this, PantallaCarga_GenerarReportes.class);
        startActivity(intent);

    }

}