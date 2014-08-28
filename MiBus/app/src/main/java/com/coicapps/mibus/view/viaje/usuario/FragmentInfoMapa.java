package com.coicapps.mibus.view.viaje.usuario;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.coicapps.mibus.R;
import com.coicapps.mibus.dao.DAOFactory;
import com.coicapps.mibus.model.Parada;
import com.coicapps.mibus.model.Posicion;
import com.coicapps.mibus.model.Viaje;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * Created by sergiojavierre on 7/19/14.
 */
public class FragmentInfoMapa extends Fragment {

    DAOFactory daoFactory;
    Context context;
    List<Posicion>listPosiciones;
    GoogleMap map;
    SharedPreferences preferences;

    List<Parada>paradaList;
    Viaje viaje;

    public static FragmentInfoMapa newInstance() {
        FragmentInfoMapa fragment = new FragmentInfoMapa();
        return fragment;
    }

    public FragmentInfoMapa() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_viaje_mapa, container, false);
        getData();
        return rootView;
    }

    private void getData(){
        context=getActivity();
        daoFactory=new DAOFactory(context);
        preferences=context.getSharedPreferences(getString(R.string.app_name),Context.MODE_PRIVATE);

        new AsyncTask<Void,Void,List<Posicion>>(){
            @Override
            protected List<Posicion> doInBackground(Void... voids) {
                Viaje viaje=new Viaje();
                viaje.setClaveViaje(preferences.getString(getString(R.string.preference_usuario_viaje),""));
                return daoFactory.getDaoPosiciones().getAllPosicionesFromViaje(viaje);
            }

            @Override
            protected void onPostExecute(List<Posicion> posicions) {
                super.onPostExecute(posicions);
                if(posicions!=null) {
                    listPosiciones = posicions;
                    setView();
                }
            }
        }.execute();


    }

    private void setView() {

        if(map==null) map=((MapFragment)
                getFragmentManager().findFragmentById(R.id.map)).getMap();
        for(int i=0;i<listPosiciones.size();i++)
        {
            Posicion pos=listPosiciones.get(i);

            LatLng posicionInicial;
            if((i+1)==listPosiciones.size()){
                posicionInicial =  new LatLng(pos.getLatitud(),pos.getLongitud());
                map.addMarker(new MarkerOptions()
                        .position(posicionInicial)
                        .title("Inicio"));
            }

            LatLng posicionActual;
            if(i==0){
                posicionActual =  new LatLng(pos.getLatitud(),pos.getLongitud());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionActual, 18.0f));
                map.addMarker(new MarkerOptions()
                        .position(posicionActual)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.app_icono))
                        .title("Bus"));
            }

            if(i>0){
                PolylineOptions lineas = new PolylineOptions()
                        .add(new LatLng(listPosiciones.get(i-1).getLatitud(),listPosiciones.get(i-1).getLongitud()))
                        .add(new LatLng(listPosiciones.get(i).getLatitud(),listPosiciones.get(i).getLongitud()));
                lineas.width(8);
                lineas.color(Color.RED);
                map.addPolyline(lineas);
            }
        }

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
                    FragmentInfoMapa.this.viaje=viaje;
                    setFinal();
                }
            }
        }.execute();

        new AsyncTask<Void,Void,List<Parada>>(){
            @Override
            protected List<Parada> doInBackground(Void... voids) {
                return daoFactory.getDAOViajes().getParadasFromViaje(preferences.getString(getString(R.string.preference_usuario_viaje), ""));
            }

            @Override
            protected void onPostExecute(List<Parada> paradas) {
                super.onPostExecute(paradas);
                if(paradas==null){
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString(getString(R.string.preference_usuario_viaje),"");
                    editor.commit();
                    getActivity().finish();
                }
                else{
                    paradaList=paradas;
                    setParadas();
                }
            }
        }.execute();

    }

    private void setFinal(){
        if(map==null) map=((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        LatLng latLngFinal=new LatLng(viaje.getLatitudFinal(),viaje.getLongitudFinal());
        map.addMarker(new MarkerOptions()
                .position(latLngFinal)
                .title("Destino"));
    }


    private void setParadas(){
        if(map==null) map=((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        for(int i=0;i<paradaList.size();i++) {
            Parada parada=paradaList.get(i);
            LatLng latLngFinal = new LatLng(parada.getLatitud(), parada.getLongitud());
            map.addMarker(new MarkerOptions()
                    .position(latLngFinal)
                    .title(parada.getNombre()));
        }
    }

}
