package com.coicapps.mibus.model;

/**
 * Created by sergiojavierre on 7/19/14.
 */
public class Mensaje {
    private String texto, fechaEmision, claveViaje;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getClaveViaje() {
        return claveViaje;
    }

    public void setClaveViaje(String claveViaje) {
        this.claveViaje = claveViaje;
    }
}
