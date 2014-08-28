package com.coicapps.mibus.dao.viajes;

import com.coicapps.mibus.model.Mensaje;
import com.coicapps.mibus.model.Parada;
import com.coicapps.mibus.model.Viaje;

import java.util.List;

/**
 * Created by sergiojavierre on 7/19/14.
 */
public interface DAOViajes {
    public Boolean createViaje(Viaje viaje, List<String> paradas);
    public Viaje getInfoFromClaveViaje(String claveViaje);
    public void stopViaje(String claveViaje);
    public void enviarMensajeAViaje(String claveViaje, String mensaje);
    public List<Mensaje> getMensajesFromViaje(String claveViaje);
    public List<Parada> getParadasFromViaje(String claveViaje);
    public void setDispositivoToViaje(String claveViaje, String idDispositivo);
    public void cancelDispositivoToViaje(String claveViaje, String idDispositivo);
}


