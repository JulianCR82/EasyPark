package com.example.easypark.model;

public class Constantes {

    public static final String NOM_BD = " EasyPark "; //nombre de la base de datos (general)
    public static final int VERSION_BD = 5; //version

    public static final String NOM_TABLA = " Registros ";
    public static final String CODIGO = " codigo ";
    public static final String FECHA = " fecha ";
    public static final String  PRECIO = " precio ";

    public static final String Consulta = "SELECT * FROM ";

    public static final String Tabla = "CREATE TABLE " + NOM_TABLA +
            " (" + CODIGO + " INTEGER PRIMARY KEY, "
            + FECHA + " TEXT NOT NULL, "
            + PRECIO + " REAL NOT NULL)";

}
