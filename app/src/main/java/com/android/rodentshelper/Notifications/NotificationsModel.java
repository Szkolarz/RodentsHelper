package com.android.rodentshelper.Notifications;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.android.rodentshelper.ROOM.Converters;

@Entity(tableName = "Notification")
public class NotificationsModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id_notification;

    @ColumnInfo(name = "id_visit")
    private Integer id_visit;

    @ColumnInfo(name = "hour")
    private Integer hour;

    @ColumnInfo(name = "minute")
    private Integer minute;

    @ColumnInfo(name = "send_time")
    private String send_time;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "unix_timestamps")
    private Long unix_timestamps;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "next_notification_time")
    private Long next_notification_time;


    //weight
    //feeding
    //visit
    @ColumnInfo(name = "notification_type")
    private String notification_type;


    public NotificationsModel(Integer id_visit, Integer hour, Integer minute, String send_time,
                              Long unix_timestamps, Long next_notification_time, String notification_type) {
        this.id_visit = id_visit;
        this.hour = hour;
        this.minute = minute;
        this.send_time = send_time;
        this.unix_timestamps = unix_timestamps;
        this.next_notification_time = next_notification_time;
        this.notification_type = notification_type;
    }

    public Integer getId_notification() {
        return id_notification;
    }

    public void setId_notification(Integer id_notification) {
        this.id_notification = id_notification;
    }

    public Integer getId_visit() {
        return id_visit;
    }

    public void setId_visit(Integer id_visit) {
        this.id_visit = id_visit;
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

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public Long getUnix_timestamps() {
        return unix_timestamps;
    }

    public void setUnix_timestamps(Long unix_timestamps) {
        this.unix_timestamps = unix_timestamps;
    }

    public Long getNext_notification_time() {
        return next_notification_time;
    }

    public void setNext_notification_time(Long next_notification_time) {
        this.next_notification_time = next_notification_time;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }
}
