package com.coicapps.mibus.dao.dispositivos;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.coicapps.mibus.R;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiojavierre on 7/19/14.
 */
public class DAODispositivoAPI implements DAODispositivo {

    Context context;

    public DAODispositivoAPI(Context context){
        this.context=context;
    }

    @Override
    public void registraDispositivo(String idGoogle) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Values.URL_SET_DISPOSITIVO);
        try {
            List<NameValuePair> postValues = new ArrayList<NameValuePair>();
            postValues.add(new BasicNameValuePair("idGoogle", idGoogle));
            httppost.setEntity(new UrlEncodedFormEntity(postValues));
            HttpResponse respuesta = httpclient.execute(httppost);
            HttpEntity entity = respuesta.getEntity();
            InputStream is = entity.getContent();
            String resultado= StreamToString.transform(is);
            resultado=resultado.replace("\n","");
            resultado=resultado.replace(" ","");
            SharedPreferences preferences=context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString(context.getString(R.string.preference_id_dispositivo),resultado);
            editor.commit();
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
}
