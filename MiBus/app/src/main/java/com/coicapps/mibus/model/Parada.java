package com.coicapps.mibus.model;

/**
 * Created by sergiojavierre on 7/20/14.
 */
public class Parada {

    private String id, nombre, claveViaje;
    private double latitud, longitud;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClaveViaje() {
        return claveViaje;
    }

    public void setClaveViaje(String claveViaje) {
        this.claveViaje = claveViaje;
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
