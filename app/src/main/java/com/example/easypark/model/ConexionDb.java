package com.example.easypark.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ConexionDb {

    public ConexionDb(Context context) {

    }

    public void onCreate(SQLiteDatabase sqLiteDatabase){ //se crean las tablas de la base de datos
        sqLiteDatabase.execSQL(Constantes.Tabla);
        sqLiteDatabase.execSQL(ConstantesIncidencias.Tabla);
    }
}
