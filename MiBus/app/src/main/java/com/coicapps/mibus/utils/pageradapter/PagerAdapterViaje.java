package com.coicapps.mibus.utils.pageradapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.coicapps.mibus.view.viaje.usuario.FragmentInfoMapa;
import com.coicapps.mibus.view.viaje.usuario.FragmentInfoViaje;

import java.util.Locale;

/**
 * Created by sergiojavierre on 7/19/14.
 */
public class PagerAdapterViaje extends FragmentPagerAdapter {

    public PagerAdapterViaje(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)return FragmentInfoViaje.newInstance();
        else if(position==1)return FragmentInfoMapa.newInstance();
        else return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "Info";
            case 1:
                return "Mapa";
        }
        return null;
    }
}