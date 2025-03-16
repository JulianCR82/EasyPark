package com.example.easypark.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Incidencia {

    private int id; // ID de la incidencia
    private Date fecha; // Fecha de la incidencia
    private String descripcion; // Detalle de la incidencia

    // Constructor con parametros
    public Incidencia(int id, Date fecha, String descripcion) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    //constructor vacio
    public Incidencia(){


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    // Método para establecer la fecha a partir de un String
    public void setFecha(String fechaString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            this.fecha = sdf.parse(fechaString); // Convertir String a Date
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Método para formatear la fecha como un String
    public String getFechaFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(fecha);
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
