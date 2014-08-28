package com.coicapps.mibus.utils;

import java.util.Calendar;

/**
 * Created by sergiojavierre on 7/19/14.
 */
public class TimeHelper {
    public static String getTimestamp()
    {
        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);
        int minutes = c.get(Calendar.MINUTE);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH)+1;
        int year = c.get(Calendar.YEAR);
        String secondsText, minutesText, hoursText, dayText, monthText, yearText;

        if(seconds<10)secondsText="0"+seconds;
        else secondsText=String.valueOf(seconds);

        if(minutes<10)minutesText="0"+minutes;
        else minutesText=String.valueOf(minutes);

        if(hours<10)hoursText="0"+hours;
        else hoursText=String.valueOf(hours);

        if(day<10)dayText="0"+day;
        else dayText=String.valueOf(day);

        if(month<10)monthText="0"+month;
        else monthText=String.valueOf(month);

        if(year<10)yearText="0"+year;
        else yearText=String.valueOf(year);
        return yearText+"-"+monthText+"-"+dayText+" "+hoursText+":"+minutesText+":"+secondsText;
    }
}
