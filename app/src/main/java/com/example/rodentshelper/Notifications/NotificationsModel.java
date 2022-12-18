package com.example.rodentshelper.Notifications;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.rodentshelper.ROOM.Converters;

import java.sql.Date;

@Entity(tableName = "Notification")
public class NotificationsModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id_notification;

    @ColumnInfo(name = "id_rodent")
    private Integer id_rodent;

    @ColumnInfo(name = "hour")
    private Integer hour;

    @ColumnInfo(name = "minute")
    private Integer minute;

    @ColumnInfo(name = "periodicity")
    private String periodicity;

    @ColumnInfo(name = "unix_timestamps")
    private String unix_timestamps;


    //weight
    //feeding
    //visit
    @ColumnInfo(name = "notification_type")
    private String notification_type;



    public NotificationsModel(Integer id_rodent, Integer hour, Integer minute, String periodicity, String unix_timestamps, String notification_type) {
        this.id_rodent = id_rodent;
        this.hour = hour;
        this.minute = minute;
        this.periodicity = periodicity;
        this.unix_timestamps = unix_timestamps;
        this.notification_type = notification_type;
    }

    public Integer getId_notification() {
        return id_notification;
    }

    public void setId_notification(Integer id_notification) {
        this.id_notification = id_notification;
    }

    public Integer getId_rodent() {
        return id_rodent;
    }

    public void setId_rodent(Integer id_rodent) {
        this.id_rodent = id_rodent;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public String getUnix_timestamps() {
        return unix_timestamps;
    }

    public void setUnix_timestamps(String unix_timestamps) {
        this.unix_timestamps = unix_timestamps;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }
}
