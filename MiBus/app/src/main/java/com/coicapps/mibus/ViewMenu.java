package com.coicapps.mibus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.coicapps.mibus.dao.DAOFactory;
import com.coicapps.mibus.utils.GCMHelper;
import com.coicapps.mibus.view.viaje.conductor.ViewBusDatos;
import com.coicapps.mibus.view.viaje.conductor.ViewBusViaje;
import com.coicapps.mibus.view.viaje.usuario.ViewViaje;


public class ViewMenu extends Activity {

    DAOFactory daoFactory;

    LinearLayout buttonBus, buttonFamiliares;
    Dialog dialogClaveViaje;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setView();
    }

    private void getData(){
        GCMHelper gcmHelper=new GCMHelper(ViewMenu.this,this);
        gcmHelper.connectToGCM();
    }

    private void setView(){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.view_menu);
        buttonBus=(LinearLayout)findViewById(R.id.menu_bus);
        buttonFamiliares=(LinearLayout)findViewById(R.id.menu_familiares);
        buttonBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchBus();
            }
        });
        buttonFamiliares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchFamiliares();
            }
        });
    }

    private void launchBus(){
        if(preferences==null)preferences=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        if(preferences.getBoolean(getString(R.string.preference_ruta_creada),false)){
            Intent intent=new Intent(this, ViewBusViaje.class);
            intent.putExtra("claveViaje",preferences.getString(getString(R.string.preference_ruta_clave),""));
            startActivity(intent);
        }
        else startActivity(new Intent(this, ViewBusDatos.class));
    }

    private void launchFamiliares(){
        if(preferences==null)preferences=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        if(preferences.getString(getString(R.string.preference_usuario_viaje),"").equals("")){
            if(daoFactory==null)daoFactory=new DAOFactory(this);
            final String idDispositivo=preferences.getString(getString(R.string.preference_id_dispositivo),"");
            dialogClaveViaje=new Dialog(this);
            dialogClaveViaje.setContentView(R.layout.dialog_clave_viaje);
            dialogClaveViaje.setTitle("Búsqueda de viaje");
            final EditText claveInput=(EditText)dialogClaveViaje.findViewById(R.id.clave_input);
            Button claveSend=(Button)dialogClaveViaje.findViewById(R.id.clave_send);
            claveSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String clave=claveInput.getText().toString();
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString(getString(R.string.preference_usuario_viaje),clave);
                    editor.commit();
                    new AsyncTask<Void,Void,Void>(){
                        @Override
                        protected Void doInBackground(Void... voids) {
                            daoFactory.getDAOViajes().setDispositivoToViaje(clave,idDispositivo);
                            return null;
                        }
                    }.execute();
                    launchViewViaje();
                    dialogClaveViaje.dismiss();
                }
            });
            dialogClaveViaje.show();
        }
        else launchViewViaje();
    }

    private void launchViewViaje(){
        startActivity(new Intent(this, ViewViaje.class));
    }
}
