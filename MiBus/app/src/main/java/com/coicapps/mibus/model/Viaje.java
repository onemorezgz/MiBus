package com.coicapps.mibus.model;

/**
 * Created by sergiojavierre on 7/19/14.
 */
public class Viaje {
    private String claveViaje, nombre, fecha, clase, horaInicio, horaFin, trayectoPeriodico, frecuenciaDePaso, cancelado, destino;
    private double latitudFinal, longitudFinal;

    public String getClaveViaje() {
        return claveViaje;
    }

    public void setClaveViaje(String claveViaje) {
        this.claveViaje = claveViaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getTrayectoPeriodico() {
        return trayectoPeriodico;
    }

    public void setTrayectoPeriodico(String trayectoPeriodico) {
        this.trayectoPeriodico = trayectoPeriodico;
    }

    public String getFrecuenciaDePaso() {
        return frecuenciaDePaso;
    }

    public void setFrecuenciaDePaso(String frecuenciaDePaso) {
        this.frecuenciaDePaso = frecuenciaDePaso;
    }

    public String getCancelado() {
        return cancelado;
    }

    public void setCancelado(String cancelado) {
        this.cancelado = cancelado;
    }

    public double getLatitudFinal() {
        return latitudFinal;
    }

    public void setLatitudFinal(double latitudFinal) {
        this.latitudFinal = latitudFinal;
    }

    public double getLongitudFinal() {
        return longitudFinal;
    }

    public void setLongitudFinal(double longitudFinal) {
        this.longitudFinal = longitudFinal;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
