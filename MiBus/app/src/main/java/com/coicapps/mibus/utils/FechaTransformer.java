package com.coicapps.mibus.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Sergio on 17/03/14.
 */
public class FechaTransformer {

    public static String transformar(String fecha) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTime(sdf.parse(fecha));// all done
        int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);
        int month=cal.get(Calendar.MONTH);
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        int minutes=cal.get(Calendar.MINUTE);

        String dayOfWeekText="";
        switch (dayOfWeek)
        {
            case 2:
            {
                dayOfWeekText="Lun";
                break;
            }
            case 3:
            {
                dayOfWeekText="Mar";
                break;
            }
            case 4:
            {
                dayOfWeekText="Mie";
                break;
            }
            case 5:
            {
                dayOfWeekText="Jue";
                break;
            }
            case 6:
            {
                dayOfWeekText="Vie";
                break;
            }
            case 7:
            {
                dayOfWeekText="Sab";
                break;
            }
            case 1:
            {
                dayOfWeekText="Dom";
                break;
            }
        }
        String monthText="",hourText="",minutesText="";
        switch (month)
        {
            case 0:
            {
                monthText="ene";
                break;
            }
            case 1:
            {
                monthText="feb";
                break;
            }
            case 2:
            {
                monthText="mar";
                break;
            }
            case 3:
            {
                monthText="abr";
                break;
            }
            case 4:
            {
                monthText="may";
                break;
            }
            case 5:
            {
                monthText="jun";
                break;
            }
            case 6:
            {
                monthText="jul";
                break;
            }
            case 7:
            {
                monthText="ago";
                break;
            }
            case 8:
            {
                monthText="sep";
                break;
            }
            case 9:
            {
                monthText="oct";
                break;
            }
            case 10:
            {
                monthText="nov";
                break;
            }
            case 11:
            {
                monthText="dic";
                break;
            }
        }

        if (hour<10) hourText="0"+hour;
        else hourText=String.valueOf(hour);
        if (minutes<10) minutesText="0"+minutes;
        else minutesText=String.valueOf(minutes);

        if(!hourText.equals("00") && !minutesText.equals("00:00"))
        {
            return dayOfWeekText+", "+dayOfMonth+" de "+monthText+", "+hourText+":"+minutesText;
        }
        else
        {
            return dayOfWeekText+", "+dayOfMonth+" de "+monthText;
        }
    }

    public static String transformarHora(String hora) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        cal.setTime(sdf.parse(hora));// all done
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        int minutes=cal.get(Calendar.MINUTE);
        String hourText,minutesText;
        if (hour<10) hourText="0"+hour;
        else hourText=String.valueOf(hour);
        if (minutes<10) minutesText="0"+minutes;
        else minutesText=String.valueOf(minutes);
        return hourText+":"+minutesText;
    }

    public static String transformarFechaConHorasMinutosSegundos(String fecha) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        cal.setTime(sdf.parse(fecha));// all done
        int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);
        int month=cal.get(Calendar.MONTH);
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        int minutes=cal.get(Calendar.MINUTE);

        String dayOfWeekText="";
        switch (dayOfWeek)
        {
            case 2:
            {
                dayOfWeekText="Lun";
                break;
            }
            case 3:
            {
                dayOfWeekText="Mar";
                break;
            }
            case 4:
            {
                dayOfWeekText="Mie";
                break;
            }
            case 5:
            {
                dayOfWeekText="Jue";
                break;
            }
            case 6:
            {
                dayOfWeekText="Vie";
                break;
            }
            case 7:
            {
                dayOfWeekText="Sab";
                break;
            }
            case 1:
            {
                dayOfWeekText="Dom";
                break;
            }
        }
        String monthText="",hourText="",minutesText="";
        switch (month)
        {
            case 0:
            {
                monthText="ene";
                break;
            }
            case 1:
            {
                monthText="feb";
                break;
            }
            case 2:
            {
                monthText="mar";
                break;
            }
            case 3:
            {
                monthText="abr";
                break;
            }
            case 4:
            {
                monthText="may";
                break;
            }
            case 5:
            {
                monthText="jun";
                break;
            }
            case 6:
            {
                monthText="jul";
                break;
            }
            case 7:
            {
                monthText="ago";
                break;
            }
            case 8:
            {
                monthText="sep";
                break;
            }
            case 9:
            {
                monthText="oct";
                break;
            }
            case 10:
            {
                monthText="nov";
                break;
            }
            case 11:
            {
                monthText="dic";
                break;
            }
        }

        if (hour<10) hourText="0"+hour;
        else hourText=String.valueOf(hour);
        if (minutes<10) minutesText="0"+minutes;
        else minutesText=String.valueOf(minutes);

        if(!hourText.equals("00") && !minutesText.equals("00:00"))
        {
            return dayOfWeekText+", "+dayOfMonth+" de "+monthText+", "+hourText+":"+minutesText;
        }
        else
        {
            return dayOfWeekText+", "+dayOfMonth+" de "+monthText;
        }
    }

    public static String transformarFecha(String fecha) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.setTime(sdf.parse(fecha));// all done
        int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);
        int month=cal.get(Calendar.MONTH);
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        int minutes=cal.get(Calendar.MINUTE);

        String dayOfWeekText="";
        switch (dayOfWeek)
        {
            case 2:
            {
                dayOfWeekText="Lunes";
                break;
            }
            case 3:
            {
                dayOfWeekText="Martes";
                break;
            }
            case 4:
            {
                dayOfWeekText="Miércoles";
                break;
            }
            case 5:
            {
                dayOfWeekText="Jueves";
                break;
            }
            case 6:
            {
                dayOfWeekText="Viernes";
                break;
            }
            case 7:
            {
                dayOfWeekText="Sábado";
                break;
            }
            case 1:
            {
                dayOfWeekText="Domingo";
                break;
            }
        }
        String monthText="",hourText="",minutesText="";
        switch (month)
        {
            case 0:
            {
                monthText="enero";
                break;
            }
            case 1:
            {
                monthText="febrero";
                break;
            }
            case 2:
            {
                monthText="marzo";
                break;
            }
            case 3:
            {
                monthText="abril";
                break;
            }
            case 4:
            {
                monthText="mayo";
                break;
            }
            case 5:
            {
                monthText="junio";
                break;
            }
            case 6:
            {
                monthText="julio";
                break;
            }
            case 7:
            {
                monthText="agosto";
                break;
            }
            case 8:
            {
                monthText="septiembre";
                break;
            }
            case 9:
            {
                monthText="octubre";
                break;
            }
            case 10:
            {
                monthText="noviembre";
                break;
            }
            case 11:
            {
                monthText="diciembre";
                break;
            }
        }

        return dayOfWeekText+", "+dayOfMonth+" de "+monthText;
    }

    public static String transformarFechaFromRSS(String fecha) throws ParseException {
     //  fecha = fecha.substring(0,25);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz",Locale.ENGLISH);
        cal.setTime(sdf.parse(fecha));// all done
        int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);
        int month=cal.get(Calendar.MONTH);
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        int minutes=cal.get(Calendar.MINUTE);

        String dayOfWeekText="";
        switch (dayOfWeek)
        {
            case 2:
            {
                dayOfWeekText="Lunes";
                break;
            }
            case 3:
            {
                dayOfWeekText="Martes";
                break;
            }
            case 4:
            {
                dayOfWeekText="Miércoles";
                break;
            }
            case 5:
            {
                dayOfWeekText="Jueves";
                break;
            }
            case 6:
            {
                dayOfWeekText="Viernes";
                break;
            }
            case 7:
            {
                dayOfWeekText="Sábado";
                break;
            }
            case 1:
            {
                dayOfWeekText="Domingo";
                break;
            }
        }
        String monthText="",hourText="",minutesText="";
        switch (month)
        {
            case 0:
            {
                monthText="enero";
                break;
            }
            case 1:
            {
                monthText="febrero";
                break;
            }
            case 2:
            {
                monthText="marzo";
                break;
            }
            case 3:
            {
                monthText="abril";
                break;
            }
            case 4:
            {
                monthText="mayo";
                break;
            }
            case 5:
            {
                monthText="junio";
                break;
            }
            case 6:
            {
                monthText="julio";
                break;
            }
            case 7:
            {
                monthText="agosto";
                break;
            }
            case 8:
            {
                monthText="septiembre";
                break;
            }
            case 9:
            {
                monthText="octubre";
                break;
            }
            case 10:
            {
                monthText="noviembre";
                break;
            }
            case 11:
            {
                monthText="diciembre";
                break;
            }
        }

        return dayOfWeekText+", "+dayOfMonth+" de "+monthText;
    }

    public static int calculateDaysDifference(Date a, Date b)
    {
        int tempDifference = 0;
        int difference = 0;
        Calendar earlier = Calendar.getInstance();
        Calendar later = Calendar.getInstance();

        if (a.compareTo(b) < 0)
        {
            earlier.setTime(a);
            later.setTime(b);
        }
        else
        {
            earlier.setTime(b);
            later.setTime(a);
        }

        while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR))
        {
            tempDifference = 365 * (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR));
            difference += tempDifference;

            earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
        }

        if (earlier.get(Calendar.DAY_OF_YEAR) != later.get(Calendar.DAY_OF_YEAR))
        {
            tempDifference = later.get(Calendar.DAY_OF_YEAR) - earlier.get(Calendar.DAY_OF_YEAR);
            difference += tempDifference;

            earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
        }

        return difference;
    }

    public static int getCurrentDayOfWeek(int dayOfWeekFromCalendar){
        switch (dayOfWeekFromCalendar){
            case 2:{
                return 0;
            }
            case 3:{
                return 1;
            }
            case 4:{
                return 2;
            }
            case 5:{
                return 3;
            }
            case 6:{
                return 4;
            }
            case 7:{
                return 5;
            }
            case 1:{
                return 6;
            }
        }
        return 0;
    }
}
