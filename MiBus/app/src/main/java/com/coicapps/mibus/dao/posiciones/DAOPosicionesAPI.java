package com.coicapps.mibus.dao.posiciones;

import android.util.Log;

import com.coicapps.mibus.model.Posicion;
import com.coicapps.mibus.model.Viaje;
import com.coicapps.mibus.utils.StreamToString;
import com.coicapps.mibus.utils.TimeHelper;
import com.coicapps.mibus.utils.Values;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiojavierre on 7/19/14.
 */
public class DAOPosicionesAPI implements DAOPosiciones {
    @Override
    public List<Posicion> getAllPosicionesFromViaje(Viaje viaje) {
        List<Posicion> listPosiciones=null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Values.URL_VIAJE_POSICIONES);
        try {
            List<NameValuePair> postValues = new ArrayList<NameValuePair>();
            postValues.add(new BasicNameValuePair("claveViaje", viaje.getClaveViaje()));
            httppost.setEntity(new UrlEncodedFormEntity(postValues));
            HttpResponse respuesta = httpclient.execute(httppost);
            HttpEntity entity = respuesta.getEntity();
            InputStream is = entity.getContent();
            String resultado= StreamToString.transform(is);
            listPosiciones=parsePosiciones(resultado);
        }
        catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listPosiciones;
    }

    @Override
    public void setPosicionToViaje(String claveViaje, double latitud, double longitud) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Values.URL_VIAJE_SET_POSICION);
        try {
            List<NameValuePair> postValues = new ArrayList<NameValuePair>();
            postValues.add(new BasicNameValuePair("claveViaje", claveViaje));
            postValues.add(new BasicNameValuePair("latitud", String.valueOf(latitud)));
            postValues.add(new BasicNameValuePair("longitud", String.valueOf(longitud)));
            postValues.add(new BasicNameValuePair("fecha", TimeHelper.getTimestamp()));

            httppost.setEntity(new UrlEncodedFormEntity(postValues));
            httpclient.execute(httppost);
            return;
        }
        catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private List<Posicion> parsePosiciones(String json) throws JSONException {
        List<Posicion>listPosiciones=new ArrayList<Posicion>();
        JSONArray lista= new JSONArray(json);
        for (int i = 0; i < lista.length(); i++)
        {
            JSONObject item= (JSONObject) lista.get(i);
            Posicion posicion=new Posicion();
            posicion.setClaveViaje(item.getString("claveViaje"));
            posicion.setLatitud(item.getDouble("latitud"));
            posicion.setLongitud(item.getDouble("longitud"));
            posicion.setFecha(item.getString("fecha"));
            listPosiciones.add(posicion);
        }
        return listPosiciones;
    }
}
