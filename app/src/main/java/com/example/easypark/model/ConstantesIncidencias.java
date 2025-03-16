package com.example.easypark.model;

import java.util.Date;

public class ConstantesIncidencias {

    public static final String NOM_TABLA = "Incidencias";
    public static final String ID_INCIDENCIA = "id_incidencia";
    public static final String FECHA = "fecha";

    public static final String DESCRIPCION = "descripcion";

    public static final String Tabla = "CREATE TABLE " + NOM_TABLA + " ("
            + ID_INCIDENCIA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FECHA + " TEXT, "
            + DESCRIPCION + " TEXT)";


}
