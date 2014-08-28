package com.coicapps.mibus.dao;

import android.content.Context;

import com.coicapps.mibus.dao.dispositivos.DAODispositivo;
import com.coicapps.mibus.dao.dispositivos.DAODispositivoAPI;
import com.coicapps.mibus.dao.posiciones.DAOPosiciones;
import com.coicapps.mibus.dao.posiciones.DAOPosicionesAPI;
import com.coicapps.mibus.dao.viajes.DAOViajes;
import com.coicapps.mibus.dao.viajes.DAOViajesAPI;

/**
 * Created by sergiojavierre on 7/19/14.
 */
public class DAOFactory {

    private Context context;
    private DAOViajes daoViajes;
    private DAOPosiciones daoPosiciones;
    private DAODispositivo daoDispositivo;

    public DAOFactory(Context context){
        this.context=context;
    }

    public DAOViajes getDAOViajes(){
        if(daoViajes==null)daoViajes=new DAOViajesAPI();
        return daoViajes;
    }

    public DAOPosiciones getDaoPosiciones(){
        if(daoPosiciones==null)daoPosiciones=new DAOPosicionesAPI();
        return daoPosiciones;
    }

    public DAODispositivo getDaoDispositivo(){
        if(daoDispositivo==null)daoDispositivo=new DAODispositivoAPI(context);
        return daoDispositivo;
    }
}
