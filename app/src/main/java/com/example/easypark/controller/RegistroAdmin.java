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

public class RegistroAdmin extends AppCompatActivity {
    Button btnIngresar;
    EditText edtUsuarioAdmin,edtContraseñaAdmin;

    private Administrador admin; // Objeto de la clase Administrador


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_admin);

        edtUsuarioAdmin = findViewById(R.id.edtUsuarioAdmin);
        edtContraseñaAdmin = findViewById(R.id.edtContraseñaAdmin);
        btnIngresar = findViewById(R.id.btnIngresar);

        // Crear un administrador con credenciales "predefinidas"
        admin = new Administrador("admin", "contraseña123");

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = edtUsuarioAdmin.getText().toString();
                String contraseña = edtContraseñaAdmin.getText().toString();

                if (validarCredenciales(usuario, contraseña)) { //si el metodo da true lo lleva a la otra pagina
                    Intent intent = new Intent(RegistroAdmin.this, HomeAdmin.class);
                    startActivity(intent);
                } else {
                    //si da false, da mensaje de error
                    Toast.makeText(RegistroAdmin.this, "Credenciales incorrectas, intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validarCredenciales(String usuario, String contraseña) {
        return admin.getUsuario().equals(usuario) && admin.getContraseña().equals(contraseña); //compara las credenciales
    }
}