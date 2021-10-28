package com.ejemplos.kotlin.examenfactum.common;
//clase con las caracteristicas de los elementos de la BD
public class MapsPojo {
    private String fecha;
    private double latitud;
    private double longitud;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
