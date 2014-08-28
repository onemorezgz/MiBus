package com.coicapps.mibus.view.viaje.usuario;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.coicapps.mibus.R;
import com.coicapps.mibus.dao.DAOFactory;
import com.coicapps.mibus.model.Mensaje;
import com.coicapps.mibus.model.Viaje;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiojavierre on 7/19/14.
 */
public class FragmentInfoViaje extends Fragment {

    DAOFactory daoFactory;
    Context context;
    View rootView;
    TextView infoViajeRuta,infoViajeInicio,infoViajeClase ,infoViajeLlegada;
    ListView infoViajeLista;
    List<Mensaje> listaMensajes;

    Viaje viaje;
    SharedPreferences preferences;

    public static FragmentInfoViaje newInstance() {
        FragmentInfoViaje fragment = new FragmentInfoViaje();
        return fragment;
    }

    public FragmentInfoViaje() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_viaje_info, container, false);
        getData();

        return rootView;


    }

    private void setView() {
        infoViajeClase = (TextView)rootView.findViewById(R.id.infoViajeCurso);
        infoViajeRuta = (TextView)rootView.findViewById(R.id.infoViajeRuta);
        infoViajeInicio = (TextView)rootView.findViewById(R.id.infoViajeInicio);
        infoViajeLlegada = (TextView)rootView.findViewById(R.id.infoViajeLlegada);
        infoViajeLista =(ListView) rootView.findViewById(R.id.infoViajeLista);

        if(viaje.getClase().equals(""))infoViajeClase.setText("-");
        else infoViajeClase.setText(viaje.getClase());
        if(viaje.getHoraFin().equals(""))infoViajeLlegada.setText("-");
        else infoViajeLlegada.setText(viaje.getHoraFin());
        if(viaje.getHoraInicio().equals(""))infoViajeInicio.setText("-");
        else infoViajeInicio.setText(viaje.getHoraInicio());

        infoViajeRuta.setText(viaje.getNombre());

        new AsyncTask<Void,Void,List<Mensaje>>(){
            @Override
            protected List<Mensaje> doInBackground(Void... voids) {
                return daoFactory.getDAOViajes().getMensajesFromViaje(viaje.getClaveViaje());
            }

            @Override
            protected void onPostExecute(List<Mensaje> mensajes) {
                super.onPostExecute(mensajes);
                listaMensajes=mensajes;
                setMensajes();
            }
        }.execute();
    }

    private void setMensajes(){
        infoViajeLista.setAdapter(new AdapterMensaje(getActivity(),listaMensajes));
    }

    private void getData(){
        context=getActivity();
        preferences=context.getSharedPreferences(getString(R.string.app_name),Context.MODE_PRIVATE);

        daoFactory=new DAOFactory(context);
        new AsyncTask<Void,Void,Viaje>(){
            @Override
            protected Viaje doInBackground(Void... voids) {
                return daoFactory.getDAOViajes().getInfoFromClaveViaje(preferences.getString(getString(R.string.preference_usuario_viaje),""));
            }

            @Override
            protected void onPostExecute(Viaje viaje) {
                super.onPostExecute(viaje);
                if(viaje==null){
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString(getString(R.string.preference_usuario_viaje),"");
                    editor.commit();
                    getActivity().finish();
                }
                else{
                    FragmentInfoViaje.this.viaje=viaje;
                    setView();
                }
            }
        }.execute();
    }
}
