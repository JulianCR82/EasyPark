package com.example.easypark.controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.easypark.R;
import com.example.easypark.model.ManagerDb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegistrarIncidencias extends AppCompatActivity {
    EditText edtIngresaDetalles;
    Button btnRegistrar, btnVolverHomeAdmin2, btnSeleccionarFecha;

    TextView txtFechaSeleccionada;
    ManagerDb managerDb;

    private Date fechaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_incidencias);

        //referenciar los componentes de la vista
        btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha);
        edtIngresaDetalles = findViewById(R.id.edtIngresaDetalles);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolverHomeAdmin2 = findViewById(R.id.btnVolverHomeAdmin2);
        txtFechaSeleccionada = findViewById(R.id.txtFechaSeleccionada);

        managerDb = new ManagerDb(this);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarIncidencia();

            }

        });

        btnVolverHomeAdmin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(RegistrarIncidencias.this,HomeAdmin.class);
                startActivity(intento);
            }
        });

        btnSeleccionarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarFecha();
            }
        });

    }

    private void guardarIncidencia(){

        //se guardan los valores en las cajas de texto
        String detallesStr = edtIngresaDetalles.getText().toString();

        //hacemos una validacion para que todos los campos esten llenos antes de hacer el registro del progreso y guardarlo
        if (detallesStr.isEmpty() || fechaSeleccionada == null) {
            Toast.makeText(RegistrarIncidencias.this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Crear un nuevo objeto Incidencia
            Incidencia nuevaIncidencia = new Incidencia();
            nuevaIncidencia.setDescripcion(detallesStr);
            nuevaIncidencia.setFecha(fechaSeleccionada);

            // Guardar la incidencia en la base de datos
            ManagerDb managerDb = new ManagerDb(RegistrarIncidencias.this);
            long resultado = managerDb.agregarIncidencia(nuevaIncidencia);

            // Si el resultado es diferente de -1, indica que se guardó exitosamente
            if (resultado != -1) {
                Toast.makeText(RegistrarIncidencias.this, "Incidencia registrada exitosamente.", Toast.LENGTH_SHORT).show();
                // Limpiar los campos de texto
                edtIngresaDetalles.setText("");
            } else {
                Toast.makeText(RegistrarIncidencias.this, "Error al guardar la incidencia.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(RegistrarIncidencias.this, "Ocurrió un error al guardar la incidencia.", Toast.LENGTH_SHORT).show();
            e.printStackTrace(); //maneja el error para que no se crashee la app
        }
    }

    private void seleccionarFecha() {

        //se crea una instancia de Calendar que representa fecha y hora actuales
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        //Se crea un objeto DatePickerDialog que muestra la pantalla para seleccionar la fecha
        DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrarIncidencias.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    //Cuando el usuario selecciona una fecha en el diálogo, se llama al método onDateSet() del OnDateSetListener
                    //Dentro de este método, se actualiza el objeto Calendar con la fecha seleccionada por el usuario. Luego, se formatea
                    //esta fecha seleccionada en el formato "yyyy-MM-dd"
                    //utilizando un objeto SimpleDateFormat, y se establece en un TextView (txtFechaSeleccionada) para mostrar al usuario.
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        fechaSeleccionada = calendar.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String fechaFormateada = sdf.format(fechaSeleccionada);
                        txtFechaSeleccionada.setText(fechaFormateada);
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

}