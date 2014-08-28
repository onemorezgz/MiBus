package com.coicapps.mibus.view.viaje.usuario;

import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.coicapps.mibus.R;
import com.coicapps.mibus.dao.DAOFactory;
import com.coicapps.mibus.utils.pageradapter.PagerAdapterViaje;

public class ViewViaje extends Activity implements ActionBar.TabListener {
    PagerAdapterViaje mSectionsPagerAdapter;
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_viaje);
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mSectionsPagerAdapter = new PagerAdapterViaje(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_viaje, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final DAOFactory daoFactory=new DAOFactory(this);
        int id = item.getItemId();
        if (id == R.id.action_remove) {
            SharedPreferences preferences=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
            final String claveViaje, idDispositivo;
            claveViaje=preferences.getString(getString(R.string.preference_usuario_viaje),"");
            idDispositivo=preferences.getString(getString(R.string.preference_id_dispositivo),"");
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString(getString(R.string.preference_usuario_viaje),"");
            editor.commit();
            new AsyncTask<Void,Void,Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    daoFactory.getDAOViajes().cancelDispositivoToViaje(claveViaje,idDispositivo);
                    return null;
                }
            }.execute();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
}
