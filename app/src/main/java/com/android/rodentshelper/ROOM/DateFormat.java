package com.android.rodentshelper.ROOM;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormat {

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat;
        // Apply desired format here
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        Calendar c = Calendar.getInstance();
        dateFormat.setTimeZone(TimeZone.getDefault());
        c.setTimeInMillis(date.getTime());
        return dateFormat.format(c.getTimeInMillis());
    }

    public static String formatTimestampToDate(Long timestamp) {
        SimpleDateFormat dateFormat;
        // Apply desired format here
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getDefault());

        return dateFormat.format(timestamp);
    }



    public static String formatDateToCalculate(Date date) {
        SimpleDateFormat dateFormat;
        // Apply desired format here
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Calendar c = Calendar.getInstance();
        dateFormat.setTimeZone(TimeZone.getDefault());
        c.setTimeInMillis(date.getTime());
        return dateFormat.format(c.getTimeInMillis());
    }

}
