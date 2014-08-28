package com.coicapps.mibus.view.viaje.usuario;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coicapps.mibus.R;
import com.coicapps.mibus.model.Mensaje;
import com.coicapps.mibus.utils.FechaTransformer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by javierjavier on 19/07/14.
 */
public class AdapterMensaje extends BaseAdapter {

    Context context;
    List<Mensaje> mensajes;

    public AdapterMensaje (Context context,List<Mensaje> mensajes){

        this.context = context;
        this.mensajes = mensajes;
    }


    @Override
    public int getCount() {
        return mensajes.size();
    }

    @Override
    public Object getItem(int position) {
        return mensajes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.array_adapter_mensaje, null,true);

        TextView titulo =(TextView)view.findViewById(R.id.adapterTitulo);
        titulo.setText(mensajes.get(position).getTexto());
        TextView fecha =(TextView)view.findViewById(R.id.adapterFecha);
        try {
            fecha.setText(FechaTransformer.transformar(mensajes.get(position).getFechaEmision()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }
}
