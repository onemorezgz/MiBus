package com.coicapps.mibus.view.viaje.conductor;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coicapps.mibus.R;
import com.coicapps.mibus.dao.DAOFactory;
import com.coicapps.mibus.utils.services.MyService;


public class ViewBusViaje extends Activity {

    String claveViaje,nombreViaje;
    DAOFactory daoFactory;

    TextView busViajeCodigo;
    Button busViajeMensaje,busViajeFinalizar;
    Dialog dialogMensaje;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setView();
    }

    private void getData(){
        preferences=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        claveViaje=this.getIntent().getExtras().getString("claveViaje");
        nombreViaje=preferences.getString(getString(R.string.preference_ruta_nombre),"");
    }

    private void setView(){
        setContentView(R.layout.view_bus_viaje);
        getActionBar().setTitle(nombreViaje);
        busViajeCodigo = (TextView) findViewById(R.id.busViajeCodigo);
        busViajeFinalizar = (Button) findViewById(R.id.busViajeTerminar);
        busViajeMensaje = (Button) findViewById(R.id.busViajeMensaje);
        busViajeCodigo.setText(claveViaje);
        busViajeFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishViaje();
            }
        });
        busViajeMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMensajeDialog();
            }
        });
    }

    private void finishViaje(){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(getString(R.string.preference_ruta_clave),"");
        editor.putString(getString(R.string.preference_ruta_nombre),"");
        editor.putBoolean(getString(R.string.preference_ruta_creada),false);
        editor.commit();
        stopService(new Intent(this, MyService.class));
        if(daoFactory==null)daoFactory=new DAOFactory(this);
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                daoFactory.getDAOViajes().stopViaje(claveViaje);
                return null;
            }
        }.execute();
        busViajeFinalizar.setVisibility(View.GONE);
    }

    private void launchMensajeDialog(){
        if(daoFactory==null)daoFactory=new DAOFactory(this);
        dialogMensaje=new Dialog(this);
        dialogMensaje.setContentView(R.layout.dialog_mensaje);
        dialogMensaje.setTitle("Mensaje");
        final EditText mensajeInput=(EditText)dialogMensaje.findViewById(R.id.mensaje_input);
        Button mensajeSend=(Button)dialogMensaje.findViewById(R.id.mensaje_send);
        mensajeSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String texto=mensajeInput.getText().toString();
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        daoFactory.getDAOViajes().enviarMensajeAViaje(claveViaje,texto);
                        return null;
                    }
                }.execute();
                mensajeInput.setText("");
                Toast.makeText(ViewBusViaje.this,"Mensaje enviado",Toast.LENGTH_SHORT).show();
                dialogMensaje.dismiss();
            }
        });
        dialogMensaje.show();
    }
}
