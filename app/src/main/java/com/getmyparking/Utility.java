package com.getmyparking;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by vaibhavjain on 05/06/17.
 */

public class Utility {

    public static Date convertTime(String inputTime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getDefault());
        Date date = null;
        try {
            date = sdf.parse(inputTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date convertDate(String dateInMilliseconds,String dateFormat) {
        String s = DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date d = null;
        try {
            d = sdf.parse(s);
        } catch (ParseException e) {

        }
        return d;
    }

}
