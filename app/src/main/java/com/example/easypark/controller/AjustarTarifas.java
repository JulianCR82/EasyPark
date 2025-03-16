package com.example.easypark.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.easypark.R;
import com.example.easypark.model.ManagerDb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class AjustarTarifas extends AppCompatActivity {

    EditText edtIngresarTarifa;
    Button btnVolverHomeAdmin, btnAjustar;

    ManagerDb managerDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ajustar_tarifas);

        edtIngresarTarifa = findViewById(R.id.edtIngresarTarifa);
        btnAjustar = findViewById(R.id.btnAjustar);
        btnVolverHomeAdmin = findViewById(R.id.btnVolverHomeAdmin);


        managerDb = new ManagerDb(AjustarTarifas.this);

        btnAjustar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajustarPrecio();
            }
        });


        btnVolverHomeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(AjustarTarifas.this,HomeAdmin.class);
                startActivity(intento);
            }
        });

    }

    private void ajustarPrecio() {
        String precioStr = edtIngresarTarifa.getText().toString(); //recuperamos el precio ingresado
        if (!precioStr.isEmpty()) {
            double nuevoPrecio = Double.parseDouble(precioStr);
            // Ajusta el precio en la base de datos
            boolean resultado = managerDb.ajustarPrecio(nuevoPrecio);
            if (resultado) {
                Toast.makeText(this, "Precio ajustado con éxito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al ajustar el precio", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingrese un precio válido", Toast.LENGTH_SHORT).show();
        }
    }
}