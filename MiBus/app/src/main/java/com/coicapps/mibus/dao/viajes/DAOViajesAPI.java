package com.coicapps.mibus.dao.viajes;

import android.util.Log;

import com.coicapps.mibus.model.Mensaje;
import com.coicapps.mibus.model.Parada;
import com.coicapps.mibus.model.Viaje;
import com.coicapps.mibus.utils.StreamToString;
import com.coicapps.mibus.utils.TimeHelper;
import com.coicapps.mibus.utils.Values;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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
public class DAOViajesAPI implements DAOViajes {

    @Override
    public Boolean createViaje(Viaje viaje, List<String> paradas) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Values.URL_VIAJE_CREATE);
        try {
            List<NameValuePair> postValues = new ArrayList<NameValuePair>();
            postValues.add(new BasicNameValuePair("claveViaje", viaje.getClaveViaje()));
            postValues.add(new BasicNameValuePair("nombre", viaje.getNombre()));
            postValues.add(new BasicNameValuePair("fecha", TimeHelper.getTimestamp()));
            postValues.add(new BasicNameValuePair("clase", viaje.getClase()));
            postValues.add(new BasicNameValuePair("horaInicio", viaje.getHoraInicio()));
            postValues.add(new BasicNameValuePair("horaFin", viaje.getHoraFin()));
            postValues.add(new BasicNameValuePair("trayectoPeriodico", viaje.getTrayectoPeriodico()));
            postValues.add(new BasicNameValuePair("frecuenciaDePaso", viaje.getFrecuenciaDePaso()));
            LatLng latLng=getLatLongFromAddress(viaje.getDestino());
            postValues.add(new BasicNameValuePair("latitudDestino", String.valueOf(latLng.latitude)));
            postValues.add(new BasicNameValuePair("longitudDestino", String.valueOf(latLng.longitude)));
            httppost.setEntity(new UrlEncodedFormEntity(postValues));
            HttpResponse respuesta = httpclient.execute(httppost);
            HttpEntity entity = respuesta.getEntity();
            InputStream is = entity.getContent();
            String resultado= StreamToString.transform(is);
            if(resultado.contains("0")) return false;
            else {
                for (int i = 0; i < paradas.size(); i++) {
                    Parada parada = new Parada();
                    parada.setClaveViaje(viaje.getClaveViaje());
                    parada.setNombre(paradas.get(i));
                    LatLng posParada = getLatLongFromAddress(parada.getNombre());
                    parada.setLatitud(posParada.latitude);
                    parada.setLongitud(posParada.longitude);
                    HttpClient httpclientParada = new DefaultHttpClient();
                    HttpPost httppostParada = new HttpPost(Values.URL_VIAJE_CREATE_PARADA);
                    List<NameValuePair> postValuesParada = new ArrayList<NameValuePair>();
                    postValuesParada.add(new BasicNameValuePair("claveViaje", viaje.getClaveViaje()));
                    postValuesParada.add(new BasicNameValuePair("nombre", parada.getNombre()));
                    postValuesParada.add(new BasicNameValuePair("latitud", String.valueOf(parada.getLatitud())));
                    postValuesParada.add(new BasicNameValuePair("longitud", String.valueOf(parada.getLongitud())));
                    httppostParada.setEntity(new UrlEncodedFormEntity(postValuesParada));
                    httpclientParada.execute(httppostParada);
                }
                return true;
            }
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
        return false;
    }

    public LatLng getLatLongFromAddress(String youraddress) {
        LatLng latLng=null;
        String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
                youraddress + "&sensor=false";
        HttpGet httpGet = new HttpGet(uri);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            double lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            double lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            Log.d("latitude", "" + lat);
            Log.d("longitude", "" + lng);
            latLng=new LatLng(lat,lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return latLng;
    }

    @Override
    public Viaje getInfoFromClaveViaje(String claveViaje) {
        Viaje viaje=null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Values.URL_VIAJE_INFO);
        try {
            List<NameValuePair> postValues = new ArrayList<NameValuePair>();
            postValues.add(new BasicNameValuePair("claveViaje", claveViaje));
            httppost.setEntity(new UrlEncodedFormEntity(postValues));
            HttpResponse respuesta = httpclient.execute(httppost);
            HttpEntity entity = respuesta.getEntity();
            InputStream is = entity.getContent();
            String resultado= StreamToString.transform(is);
            viaje=parseViaje(resultado);
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
        return viaje;
    }

    @Override
    public void stopViaje(String claveViaje) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Values.URL_VIAJE_CANCEL);
        try {
            List<NameValuePair> postValues = new ArrayList<NameValuePair>();
            postValues.add(new BasicNameValuePair("claveViaje", claveViaje));
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

    @Override
    public void enviarMensajeAViaje(String claveViaje, String mensaje) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Values.URL_VIAJE_SET_MENSAJE);
        try {
            List<NameValuePair> postValues = new ArrayList<NameValuePair>();
            postValues.add(new BasicNameValuePair("claveViaje", claveViaje));
            postValues.add(new BasicNameValuePair("texto", mensaje));
            postValues.add(new BasicNameValuePair("fechaEmision", TimeHelper.getTimestamp()));
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

    @Override
    public List<Mensaje> getMensajesFromViaje(String claveViaje) {
        List<Mensaje> listMensajes=null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Values.URL_VIAJE_GET_MENSAJES);
        try {
            List<NameValuePair> postValues = new ArrayList<NameValuePair>();
            postValues.add(new BasicNameValuePair("claveViaje", claveViaje));
            httppost.setEntity(new UrlEncodedFormEntity(postValues));
            HttpResponse respuesta = httpclient.execute(httppost);
            HttpEntity entity = respuesta.getEntity();
            InputStream is = entity.getContent();
            String resultado= StreamToString.transform(is);
            listMensajes=parseMensajes(resultado);
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
        return listMensajes;
    }

    @Override
    public List<Parada> getParadasFromViaje(String claveViaje) {
        List<Parada> listParadas=null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Values.URL_VIAJE_GET_PARADAS);
        try {
            List<NameValuePair> postValues = new ArrayList<NameValuePair>();
            postValues.add(new BasicNameValuePair("claveViaje", claveViaje));
            httppost.setEntity(new UrlEncodedFormEntity(postValues));
            HttpResponse respuesta = httpclient.execute(httppost);
            HttpEntity entity = respuesta.getEntity();
            InputStream is = entity.getContent();
            String resultado= StreamToString.transform(is);
            listParadas=parseParadas(resultado);
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
        return listParadas;
    }

    @Override
    public void setDispositivoToViaje(String claveViaje, String idDispositivo) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Values.URL_VIAJE_DISPOSITIVO);
        try {
            List<NameValuePair> postValues = new ArrayList<NameValuePair>();
            postValues.add(new BasicNameValuePair("claveViaje", claveViaje));
            postValues.add(new BasicNameValuePair("idDispositivo", idDispositivo));
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

    @Override
    public void cancelDispositivoToViaje(String claveViaje, String idDispositivo) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Values.URL_CANCEL_VIAJE_DISPOSITIVO);
        try {
            List<NameValuePair> postValues = new ArrayList<NameValuePair>();
            postValues.add(new BasicNameValuePair("claveViaje", claveViaje));
            postValues.add(new BasicNameValuePair("idDispositivo", idDispositivo));
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

    private Viaje parseViaje(String json) throws JSONException {
        Viaje viaje=null;
        JSONArray lista= new JSONArray(json);
        for (int i = 0; i < lista.length(); i++)
        {
            JSONObject item= (JSONObject) lista.get(i);
            viaje=new Viaje();
            viaje.setClaveViaje(item.getString("claveViaje"));
            viaje.setNombre(item.getString("nombre"));
            viaje.setFecha(item.getString("fecha"));
            viaje.setClase(item.getString("clase"));
            viaje.setHoraInicio(item.getString("horaInicio"));
            viaje.setHoraFin(item.getString("horaFin"));
            viaje.setTrayectoPeriodico(item.getString("trayectoPeriodico"));
            viaje.setFrecuenciaDePaso(item.getString("frecuenciaDePaso"));
            viaje.setLatitudFinal(item.getDouble("latitudFinal"));
            viaje.setLongitudFinal(item.getDouble("longitudFinal"));
        }
        return viaje;
    }

    private List<Mensaje> parseMensajes(String json) throws JSONException {
        List<Mensaje>listPosiciones=new ArrayList<Mensaje>();
        JSONArray lista= new JSONArray(json);
        for (int i = 0; i < lista.length(); i++)
        {
            JSONObject item= (JSONObject) lista.get(i);
            Mensaje mensaje=new Mensaje();
            mensaje.setTexto(item.getString("texto"));
            mensaje.setFechaEmision(item.getString("fechaEmision"));
            mensaje.setClaveViaje(item.getString("claveViaje"));
            listPosiciones.add(mensaje);
        }
        return listPosiciones;
    }

    private List<Parada> parseParadas(String json) throws JSONException {
        List<Parada>listParadas=new ArrayList<Parada>();
        JSONArray lista= new JSONArray(json);
        for (int i = 0; i < lista.length(); i++)
        {
            JSONObject item= (JSONObject) lista.get(i);
            Parada parada=new Parada();
            parada.setNombre(item.getString("nombre"));
            parada.setClaveViaje(item.getString("claveViaje"));
            parada.setLatitud(item.getDouble("latitud"));
            parada.setLongitud(item.getDouble("longitud"));
            listParadas.add(parada);
        }
        return listParadas;
    }
}
