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

public class HomeUsuario extends AppCompatActivity {

    Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_usuario);

        btnSalir = findViewById(R.id.btnSalir);

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeUsuario.this, Principal.class);
                startActivity(intent);
            }
        });

    }

    public void irPagar(View view){
        Intent intent = new Intent(HomeUsuario.this, PantallaCarga_Pagos.class);
        startActivity(intent);
    }

    public void irExpedirCodigo(View view){
        Intent intent = new Intent(HomeUsuario.this, PantallaCarga_ExpedirCodigo.class);
        startActivity(intent);

    }




}