package com.example.easypark.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.easypark.controller.Incidencia;
import com.example.easypark.controller.Registro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ManagerDb {

    DbHelper dbHelper;
    SQLiteDatabase db;

    public ManagerDb(Context context){

        //hago el llamado a la conexion de la bd
        dbHelper = new DbHelper(context);

    }

    //metodo para abrir la base de datos en modo escritura
    public void openBdWr(){
        db = dbHelper.getWritableDatabase();
    }

    //metodo para abrir la base de datos en modo lectura
    public void openBdRd(){
        db = dbHelper.getReadableDatabase();
    }

    public void closeDb(){
        if (db!= null){
            db.close();
        }
    }

    //metodo para ingresar la incidencia
    public long agregarIncidencia(Incidencia incidencia) {

        // Abre la base de datos en modo escritura
        openBdWr();

        // Crea un ContentValues para insertar los datos
        ContentValues values = new ContentValues();
        values.put(ConstantesIncidencias.FECHA, incidencia.getFechaFormatted()); // Convertir Date a long
        values.put(ConstantesIncidencias.DESCRIPCION, incidencia.getDescripcion());

        long resultado = db.insert(ConstantesIncidencias.NOM_TABLA, null, values);
        closeDb();
        return resultado;


    }

    public ArrayList<Incidencia> listarDatos(String id_incidencia){
        openBdWr(); // abrir la BD

        ArrayList<Incidencia> arrayList = new ArrayList<>(); // Lista para guardar los datos

        // Configurar la consulta para obtener la incidencia seleccionada
        Cursor cursor = db.rawQuery("SELECT * FROM " + ConstantesIncidencias.NOM_TABLA + " WHERE " + ConstantesIncidencias.ID_INCIDENCIA + " = ?", new String[]{id_incidencia});

        if (cursor.moveToFirst()){ // verificar si el cursor tiene datos
            do {
                Incidencia leng = new Incidencia(); // Nueva instancia en cada iteración
                leng.setId(cursor.getInt(0)); // Conversión correcta a entero
                leng.setFecha(cursor.getString(1)); // Conversión de String a Date dentro de Incidencia
                leng.setDescripcion(cursor.getString(2));

                arrayList.add(leng); // agregar a la lista

            } while (cursor.moveToNext());
        }
        cursor.close(); // Cerrar el cursor
        closeDb(); // Cerrar la BD
        return arrayList; // Retornar la lista
    }

    public ArrayList<Incidencia> obtenerTodasLasIncidencias(){
        openBdRd(); // abrir la base de datos en modo lectura

        ArrayList<Incidencia> incidenciasList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ConstantesIncidencias.NOM_TABLA, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Incidencia incidencia = new Incidencia(); // Nueva instancia de Incidencia
                incidencia.setId(cursor.getInt(0)); // Conversión correcta
                incidencia.setFecha(cursor.getString(1)); // Conversión de String a Date
                incidencia.setDescripcion(cursor.getString(2));

                incidenciasList.add(incidencia); // Agregar a la lista
            } while (cursor.moveToNext());
            cursor.close(); // Cerrar el cursor
        }

        closeDb(); // Cerrar la base de datos

        return incidenciasList; // Retornar la lista
    }

    // Método para ajustar el precio en la base de datos
    public boolean ajustarPrecio(double nuevoPrecio) {
        openBdWr();
        ContentValues values = new ContentValues();
        values.put(Constantes.PRECIO, nuevoPrecio); // guarda el dato en la columna precio de la base de datos

        long resultado = db.update(Constantes.NOM_TABLA, values, null, null); // Actualiza la tabla Registros
        db.close();
        return resultado != -1; // Retorna verdadero si la actualización fue exitosa
    }

    public boolean guardarRegistro(Registro registro) {
        openBdWr();
        ContentValues values = new ContentValues();
        values.put(Constantes.CODIGO, registro.getCodigo());

        // Convertir la fecha de Date a String
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String fechaStr = sdf.format(registro.getFecha()); // Convierte el Date a String
        values.put(Constantes.FECHA, fechaStr); // Inserta la fecha formateada como String

        values.put(Constantes.PRECIO, registro.getPrecio());

        long resultado = db.insert(Constantes.NOM_TABLA, null, values);
        closeDb();
        return resultado != -1; // Retorna verdadero si la inserción fue exitosa
    }

    // Método para obtener el precio actual de la base de datos
    public double obtenerPrecio() {
        openBdRd();
        double precio = 0; //inicializa la variable precio en ceros
        Cursor cursor = db.query(Constantes.NOM_TABLA, new String[]{Constantes.PRECIO}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) { // Extrae el valor de la primera columna del primer resultado como un 'double'.
            precio = cursor.getDouble(0);
            cursor.close();
        }
        return precio; // Devuelve el precio actual
    }

    public ArrayList<Integer> obtenerTodosLosCodigos() {
        openBdRd(); // Abrir la base de datos en modo lectura

        ArrayList<Integer> codigosList = new ArrayList<>(); // Lista para guardar los códigos

        Cursor cursor = db.rawQuery("SELECT " + Constantes.CODIGO + " FROM " + Constantes.NOM_TABLA, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int codigo = cursor.getInt(0); // Obtener el código como entero
                codigosList.add(codigo); // Agregar a la lista
            } while (cursor.moveToNext());
            cursor.close(); // Cerrar el cursor
        }

        closeDb(); // Cerrar la base de datos

        return codigosList; // Retornar la lista
    }


    public ArrayList<Registro> obtenerRegistrosPorCodigo(int codigo) {
        openBdRd(); // abrir la BD en modo lectura
        ArrayList<Registro> registrosList = new ArrayList<>(); // Lista para guardar los datos

        // Consultar registros por código
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constantes.NOM_TABLA + " WHERE " + Constantes.CODIGO + " = ?", new String[]{String.valueOf(codigo)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Registro registro = new Registro(); // Nueva instancia de Registro
                // la estructura de la tabla tiene los campos en el siguiente orden
                registro.setCodigo(cursor.getInt(0)); // el código está en la primera columna

                // Convertir el String a Date
                String fechaStr = cursor.getString(1); // La fecha como String
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); // Ajusta el formato de la fecha
                try {
                    Date fecha = sdf.parse(fechaStr); // Convierte el String a Date
                    registro.setFecha(fecha); // Asigna el Date a la instancia de Registro
                } catch (ParseException e) {
                    e.printStackTrace(); // Maneja la excepción si el formato de la fecha es incorrecto
                }

                registro.setPrecio(cursor.getDouble(2)); // Precio como double

                registrosList.add(registro); // Agregar a la lista
            } while (cursor.moveToNext());
            cursor.close(); // Cerrar el cursor
        }

        closeDb(); // Cerrar la BD
        return registrosList; // Retornar la lista
    }

    public ArrayList<Registro> obtenerRegistros() {
        openBdRd();
        ArrayList<Registro> registrosList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constantes.NOM_TABLA, null);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Registro registro = new Registro();
                registro.setCodigo(cursor.getInt(0));

                // Convertir el String de la base de datos a Date
                String fechaStr = cursor.getString(1);
                try {
                    Date fecha = sdf.parse(fechaStr);
                    registro.setFecha(fecha); // Asignar el Date a la instancia de Registro
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Maneja el error
                    registro.setFecha(new Date()); // Usando la fecha actual como ejemplo
                }

                registro.setPrecio(cursor.getDouble(2));
                registrosList.add(registro);
            } while (cursor.moveToNext());
            cursor.close();
        }

        closeDb();
        return registrosList;
    }
}
