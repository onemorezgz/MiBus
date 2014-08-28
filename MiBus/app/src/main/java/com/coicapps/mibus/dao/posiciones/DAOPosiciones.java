package com.coicapps.mibus.dao.posiciones;

import com.coicapps.mibus.model.Posicion;
import com.coicapps.mibus.model.Viaje;

import java.util.List;

/**
 * Created by sergiojavierre on 7/19/14.
 */
public interface DAOPosiciones {

    public List<Posicion> getAllPosicionesFromViaje(Viaje viaje);
    public void setPosicionToViaje(String claveViaje, double latitud, double longitud);
}
