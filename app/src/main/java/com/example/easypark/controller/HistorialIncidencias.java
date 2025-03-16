package com.example.easypark.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.easypark.R;
import com.example.easypark.model.ManagerDb;

import java.util.ArrayList;

public class HistorialIncidencias extends AppCompatActivity {

    Button btnVolverHomeAdmin3;
    Spinner spIncidencias;
    ListView listIncidencias;

    ManagerDb managerDb;

    ArrayAdapter<String> spinnerAdapter;
    ArrayAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historial_incidencias);

        btnVolverHomeAdmin3 = findViewById(R.id.btnVolverHomeAdmin3); //referenciacion de componentes
        spIncidencias = findViewById(R.id.spIncidencias);
        listIncidencias = findViewById(R.id.listIncidencias);

        btnVolverHomeAdmin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistorialIncidencias.this, HomeAdmin.class);
                startActivity(intent);
            }
        });

        managerDb = new ManagerDb(this);

        //configuramos el spinner
        cargarIdIncidencias();

        spIncidencias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nombreLenguaje = parent.getItemAtPosition(position).toString();
                mostrarInfoIncidencia(nombreLenguaje);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void cargarIdIncidencias() {
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<Incidencia> incidenciasList = managerDb.obtenerTodasLasIncidencias();
        if (incidenciasList != null) {
            for (Incidencia incidencia : incidenciasList) {
                ids.add(String.valueOf(incidencia.getId())); // Convertir int a String
            }
        }
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ids);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIncidencias.setAdapter(spinnerAdapter); //carga los ids al spinner
    }

    private void mostrarInfoIncidencia(String nombreLenguaje) {
        // Recupera una lista de incidencias relacionadas con el nombre pasado como parámetro.
        ArrayList<Incidencia> lenguajesList = managerDb.listarDatos(nombreLenguaje);
        if (lenguajesList != null && !lenguajesList.isEmpty()) {
            // Suponemos que solo hay una incidencia con el mismo ID
            Incidencia incidencia = lenguajesList.get(0);

            // Actualizar los datos en el adaptador del ListView
            // Crea una lista para almacenar la información formateada de la incidencia.
            ArrayList<String> datosIncidencia = new ArrayList<>();
            datosIncidencia.add("Código:"); // Añade la etiqueta
            datosIncidencia.add(String.valueOf(incidencia.getId())); // Añade el valor en la siguiente línea
            datosIncidencia.add("Fecha:");
            datosIncidencia.add(incidencia.getFechaFormatted()); // Formatea la fecha
            datosIncidencia.add("Descripción:");
            datosIncidencia.add(incidencia.getDescripcion()); // Añade la descripción

            // Crear o actualizar el adaptador del ListView
            if (listAdapter == null) {
                listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datosIncidencia);
                listIncidencias.setAdapter(listAdapter);
            } else {
                listAdapter.clear();
                listAdapter.addAll(datosIncidencia);
                listAdapter.notifyDataSetChanged();
            }

        }
    }


}