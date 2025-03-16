package com.example.easypark.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.easypark.R;
import com.example.easypark.model.ManagerDb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class ExpedirCodigo extends AppCompatActivity {

    Button btnVolver,btnExpedirCodigo; //instanciamos los dos botones

    ManagerDb managerDb; //llamamos a la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expedir_codigo);

        btnVolver = findViewById(R.id.btnVolver); //referenciamos los componentes y a la base de datos
        btnExpedirCodigo = findViewById(R.id.btnExpedirCodigo);
        managerDb = new ManagerDb(this);

        btnExpedirCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expedirCodigo(); //se llama al metodo expedir codigo
            }
        });



        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intento = new Intent(ExpedirCodigo.this,HomeUsuario.class);
                startActivity(intento); //se crea el intent para volver al layout del homeUsuario

            }
        });

    }

    private void expedirCodigo() {
        int codigoGenerado = generarCodigo(); // Método para generar el código
        double precio = managerDb.obtenerPrecio(); // Obtener el precio actual desde el ManagerDb
        String fechaStr = obtenerFechaActual(); // Método para obtener la fecha actual

        // Convertir String a Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date fecha = null;
        try {
            fecha = sdf.parse(fechaStr); // Convierte el String a Date
        } catch (ParseException e) {
            e.printStackTrace(); //el catch maneja el error (en caso de que pase) para que no crashee la app
        }

        Registro nuevoRegistro = new Registro(codigoGenerado, fecha, precio);
        // Guarda el registro en la base de datos
        boolean resultado = managerDb.guardarRegistro(nuevoRegistro);
        if (resultado) {
            Toast.makeText(this, "Código expedido: " + codigoGenerado, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al expedir el código", Toast.LENGTH_SHORT).show();
        }
    }


    private int generarCodigo() {
        // Genera un código aleatorio (puedes usar otra lógica si lo deseas)
        return new Random().nextInt(100000); // Código aleatorio de 5 dígitos
    }

    private String obtenerFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date()); // Devuelve la fecha en formato yyyy-MM-dd (año-mes-dia)
    }


}