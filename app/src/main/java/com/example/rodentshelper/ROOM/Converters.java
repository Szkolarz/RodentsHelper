package com.example.rodentshelper.ROOM;

import androidx.room.TypeConverter;

import java.sql.Date;

public class Converters {


    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
