package com.example.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;

import com.example.rodentshelper.Notifications.NotificationsModel;
import com.example.rodentshelper.ROOM.Weights.WeightModel;
import com.example.rodentshelper.ROOM._MTM._RodentWeight.RodentWithWeights;

import java.sql.Date;
import java.util.List;

@Dao
public interface DAONotifications {

    @Insert
    void insertRecordNotification(NotificationsModel Notification);


    @Query("SELECT hour FROM Notification WHERE notification_type = 'weight'")
    Integer getHourFromNotificationWeight();

    @Query("SELECT minute FROM Notification WHERE notification_type = 'weight'")
    Integer getMinuteFromNotificationWeight();

    @Query("SELECT periodicity FROM Notification WHERE notification_type = 'weight'")
    String getPeriodicityFromNotificationWeight();

    @Query("SELECT unix_timestamps FROM Notification WHERE notification_type = 'weight'")
    Long getUnixTimestampsFromNotificationWeight();


    @Query("UPDATE Notification SET unix_timestamps = :unix_timestamps WHERE notification_type = 'weight'")
    void updateUnixTimestamp(Long unix_timestamps);





    @Query("SELECT hour FROM Notification WHERE notification_type = 'feeding'")
    List<NotificationsModel> getHourFromNotificationFeeding();

    @Query("SELECT minute FROM Notification WHERE notification_type = 'feeding'")
    List<NotificationsModel> getMinuteFromNotificationFeeding();



    @Query("SELECT hour FROM Notification WHERE notification_type = 'visit' AND id_rodent = :id_rodent")
    List<NotificationsModel> getHourFromNotificationVisit(Integer id_rodent);

    @Query("SELECT minute FROM Notification WHERE notification_type = 'visit' AND id_rodent = :id_rodent")
    List<NotificationsModel> getMinuteFromNotificationVisit(Integer id_rodent);



    @Query("DELETE FROM Notification WHERE id_rodent = :id_rodent")
    void deleteNotificationByRodentId(Integer id_rodent);

    @Query("DELETE FROM Notification WHERE notification_type = 'weight'")
    void deleteNotificationWeight();

    @Query("DELETE FROM Notification WHERE notification_type = 'feeding'")
    void deleteNotificationFeeding();

}


