package com.coicapps.mibus.model;

/**
 * Created by sergiojavierre on 7/19/14.
 */
public class Posicion {

    private double latitud,longitud;
    private String fecha, claveViaje;

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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getClaveViaje() {
        return claveViaje;
    }

    public void setClaveViaje(String claveViaje) {
        this.claveViaje = claveViaje;
    }
}
