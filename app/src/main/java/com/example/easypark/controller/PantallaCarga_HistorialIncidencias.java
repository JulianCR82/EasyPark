package com.example.easypark.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.easypark.R;

public class PantallaCarga_HistorialIncidencias extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 4000; // Duración del splash en milisegundos (4 segundos)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_carga_historial_incidencias);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Después del tiempo especificado, inicia la actividad principal
                Intent intent = new Intent(PantallaCarga_HistorialIncidencias.this, HistorialIncidencias.class);
                startActivity(intent);
                finish(); // Cierra la actividad splash para que no pueda ser vuelta atrás
            }
        }, SPLASH_TIME_OUT);

    }
}