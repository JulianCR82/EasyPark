package com.example.easypark.controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.easypark.R;
import com.example.easypark.model.ManagerDb;

import java.io.File;
import java.util.List;

public class GenerarReportes extends AppCompatActivity {

    Button btnGenerarReportes, btnVolverHomeAdmin4;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_generar_reportes);

        // Referenciación de los componentes
        btnVolverHomeAdmin4 = findViewById(R.id.btnVolverHomeAdmin4);
        btnGenerarReportes = findViewById(R.id.btnGenerarReportes);

        // Se llama al metodo para pedir permisos en caso de que sea necesario
        checkPermissions();

        btnGenerarReportes.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
                    ContextCompat.checkSelfPermission(GenerarReportes.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                generarReportePDF();
            } else {
                Toast.makeText(GenerarReportes.this, "Permisos no otorgados.", Toast.LENGTH_SHORT).show();
            }
        });

        btnVolverHomeAdmin4.setOnClickListener(v -> {
            Intent intent = new Intent(GenerarReportes.this, HomeAdmin.class);
            startActivity(intent);
        });
    }

    private void checkPermissions() { //metodo para solcitar permisos
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && //la Q pertenece a android 10
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }
    }

    //verificacion de que el sistema tenga permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso de escritura otorgado.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso de escritura denegado.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void generarReportePDF() {
        ManagerDb managerDb = new ManagerDb(this);
        // Llama al método 'obtenerRegistros' de ManagerDb para recuperar una lista de registros almacenados en la base de datos.
        List<Registro> registros = managerDb.obtenerRegistros();

        if (registros != null && !registros.isEmpty()) { // Si hay registros, llama al método 'createPdf' de la clase PdfGenerador.
            PdfGenerador.createPdf(this, registros);
            Toast.makeText(this, "Reporte PDF generado correctamente.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No hay datos disponibles para generar el reporte.", Toast.LENGTH_SHORT).show();
        }
    }
}

