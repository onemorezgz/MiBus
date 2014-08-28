package com.coicapps.mibus.view.viaje.conductor;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.coicapps.mibus.R;
import com.coicapps.mibus.dao.DAOFactory;
import com.coicapps.mibus.model.Parada;
import com.coicapps.mibus.model.Viaje;
import com.coicapps.mibus.utils.services.MyService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javierjavier on 19/07/14.
 */
public class ViewBusDatos extends Activity{

    EditText busDatosCodigo,busDatosClase, busDatosLlegada,busDatosSalida,busDatosRuta, busDatosDestino;
    Button busDatosCrear, busDatosAddParadas;
    ProgressDialog progressDialog;

    String codigo, clase, llegada, salida,nombre;

    DAOFactory daoFactory;
    Viaje viaje;
    Dialog dialogParadas;
    TextView textParadas;
    List<String>paradas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_bus_datos);

        busDatosCrear = (Button) findViewById(R.id.busDatosCrear);
        busDatosAddParadas = (Button) findViewById(R.id.busDatosAdd);
        busDatosClase = (EditText) findViewById(R.id.busDatosClase);
        busDatosCodigo = (EditText) findViewById(R.id.busDatosCodigo);
        busDatosLlegada = (EditText) findViewById(R.id.busDatosLlegada);
        busDatosSalida = (EditText) findViewById(R.id.busDatosSalida);
        busDatosDestino = (EditText) findViewById(R.id.busDatosDestino);
        busDatosRuta = (EditText) findViewById(R.id.busDatosRuta);
        textParadas = (TextView) findViewById(R.id.busDatosParadas);
        paradas=new ArrayList<String>();

        busDatosCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                codigo = busDatosCodigo.getText().toString();
                clase = busDatosClase.getText().toString();
                llegada = busDatosLlegada.getText().toString();
                salida = busDatosSalida.getText().toString();
                nombre = busDatosRuta.getText().toString();

                viaje=new Viaje();
                viaje.setClaveViaje(codigo);
                viaje.setClase(clase);
                viaje.setHoraFin(llegada);
                viaje.setHoraInicio(salida);
                viaje.setNombre(nombre);
                viaje.setFrecuenciaDePaso("0");
                viaje.setTrayectoPeriodico("0");
                viaje.setDestino(busDatosDestino.getText().toString());

                if (!viaje.getClaveViaje().isEmpty() && !viaje.getNombre().isEmpty() && !viaje.getDestino().isEmpty()){
                    checkViaje();
                }else{
                    Toast.makeText(getApplicationContext(),"Rellene los campos", Toast.LENGTH_LONG).show();
                }
            }
        });

        busDatosAddParadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDialogParadas();
            }
        });
    }

    private void checkViaje(){
        progressDialog = ProgressDialog.show(this, "", "Registrando viaje");
        if(daoFactory==null)daoFactory=new DAOFactory(this);
        new AsyncTask<Void,Void,Boolean>(){
            @Override
            protected Boolean doInBackground(Void... voids) {
                return daoFactory.getDAOViajes().createViaje(viaje,paradas);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if(aBoolean)launchService();
                else errorClaveViaje();
                progressDialog.dismiss();
            }
        }.execute();

    }

    private void launchService(){
        startService(new Intent(this, MyService.class));
        SharedPreferences preferences=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(getString(R.string.preference_ruta_creada),true);
        editor.putString(getString(R.string.preference_ruta_clave), viaje.getClaveViaje());
        editor.putString(getString(R.string.preference_ruta_nombre), viaje.getNombre());
        editor.commit();
        finish();
        Toast.makeText(this,"El viaje ha comenzado",Toast.LENGTH_LONG).show();
    }

    private void errorClaveViaje(){
        Toast.makeText(this,"La clave del viaje ya existe. Pruebe con otra diferente.",Toast.LENGTH_LONG).show();
    }

    private void launchDialogParadas(){
        dialogParadas=new Dialog(this);
        dialogParadas.setContentView(R.layout.dialog_parada);
        dialogParadas.setTitle("Añadir parada");
        final EditText claveInput=(EditText)dialogParadas.findViewById(R.id.parada_input);
        Button claveSend=(Button)dialogParadas.findViewById(R.id.parada_send);
        claveSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paradas.add(claveInput.getText().toString());
                textParadas.setText("");
                for(int i=0;i<paradas.size();i++){
                    String currentText=textParadas.getText().toString();
                    if((i+1)<paradas.size())textParadas.setText(currentText+paradas.get(i)+",");
                    else textParadas.setText(currentText+paradas.get(i));
                }
                dialogParadas.dismiss();
            }
        });
        dialogParadas.show();
    }

}
