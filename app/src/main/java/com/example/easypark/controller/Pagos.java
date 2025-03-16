package com.example.easypark.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.easypark.R;
import com.example.easypark.model.ManagerDb;

import java.util.ArrayList;

public class Pagos extends AppCompatActivity {

    Button btnPagar, btnVolverHome2;
    Spinner spCodigos;

    ListView listRegistros;

    ManagerDb managerDb;

    ArrayAdapter<String> spinnerAdapter;
    ArrayAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagos);

        btnPagar = findViewById(R.id.btnPagar);
        btnVolverHome2 = findViewById(R.id.btnVolverHome2);
        spCodigos = findViewById(R.id.spCodigos);
        listRegistros = findViewById(R.id.listRegistros);

        managerDb = new ManagerDb(this);

        cargarCodigos();
        // Configuramos el listener para el spinner
        spCodigos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String codigoSeleccionado = parent.getItemAtPosition(position).toString();
                int codigo = Integer.parseInt(codigoSeleccionado); // Convertir a int
                mostrarInfoRegistro(codigo); // Llamar al método con el int
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intento = new Intent(Pagos.this,MetodosDePago.class);
                startActivity(intento);

            }
        });

        btnVolverHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(Pagos.this,HomeUsuario.class);
                startActivity(intento);
            }
        });


    }

    private void cargarCodigos() {
        ArrayList<Integer> codigos = managerDb.obtenerTodosLosCodigos(); // Obtener códigos como enteros

        if (codigos != null) { //si codigos es diferente de null
            ArrayList<String> codigosString = new ArrayList<>(); // Lista para códigos como String
            for (Integer codigo : codigos) { //se recorren todos los codigos
                codigosString.add(String.valueOf(codigo)); // Convertir a String
            }

            spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, codigosString);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCodigos.setAdapter(spinnerAdapter);
        }
    }

    private void mostrarInfoRegistro(int codigo) { // Cambiado a int
        // Obtener la información del registro basado en el código seleccionado
        ArrayList<Registro> registrosList = managerDb.obtenerRegistrosPorCodigo(codigo);
        if (registrosList != null && !registrosList.isEmpty()) {
            // Suponemos que solo hay un registro con el mismo código
            Registro registro = registrosList.get(0);

            // Actualizar los datos en el adaptador del ListView
            ArrayList<String> datosRegistro = new ArrayList<>();
            datosRegistro.add("Código: " + registro.getCodigo()); // Añade el código
            datosRegistro.add("Fecha: " + registro.getFechaFormatted()); // Formatea la fecha y la añade
            datosRegistro.add("Precio: " + registro.getPrecio()); // Añade el precio

            // Crear o actualizar el adaptador del ListView
            if (listAdapter == null) {
                listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datosRegistro);
                listRegistros.setAdapter(listAdapter);
            } else {
                listAdapter.clear();
                listAdapter.addAll(datosRegistro);
                listAdapter.notifyDataSetChanged();
            }
        }
    }
}





