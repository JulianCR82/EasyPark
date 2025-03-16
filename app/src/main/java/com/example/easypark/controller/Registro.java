package com.example.easypark.controller;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Registro {

    private int codigo; //atributos
    private Date fecha;
    private double precio;

    public Registro(int codigo, Date fecha, double precio) { //constructor con parametros
        this.codigo = codigo;
        this.fecha = fecha;
        this.precio = precio;
    }

    public Registro() { //constructor vacio
    }

    public int getCodigo() {
        return codigo;
    } //getters

    public Date getFecha() {
        return fecha;
    }

    public double getPrecio() {
        return precio;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    } //setters

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Método para formatear la fecha como un String
    public String getFechaFormatted() {
        if (fecha == null) {
            Log.e("Registro", "La fecha es null");
            return "Fecha no disponible";
        }
        Log.d("Registro", "La fecha es: " + fecha.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(fecha);
    }
}

