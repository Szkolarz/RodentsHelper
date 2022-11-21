package com.example.rodentshelper.ROOM;

import androidx.room.TypeConverter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Converters {


    @TypeConverter
    public static Date toDate(Long timestamp) {



        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {

        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat;
        // Apply desired format here
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        Calendar c = Calendar.getInstance();
        dateFormat.setTimeZone(TimeZone.getDefault());
        c.setTimeInMillis(date.getTime());
        return dateFormat.format(c.getTimeInMillis());
    }
}
