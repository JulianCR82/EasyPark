package com.example.easypark.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, Constantes.NOM_BD, null, Constantes.VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Crear tabla Registros
        sqLiteDatabase.execSQL(Constantes.Tabla);

        // Crear tabla Incidencias
        sqLiteDatabase.execSQL(ConstantesIncidencias.Tabla);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si actualizas la base de datos, elimina las tablas existentes y crea de nuevo
        db.execSQL("DROP TABLE IF EXISTS " + Constantes.NOM_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + ConstantesIncidencias.NOM_TABLA);


        // Vuelve a crear las tablas con la nueva estructura
        onCreate(db);
    }
}
