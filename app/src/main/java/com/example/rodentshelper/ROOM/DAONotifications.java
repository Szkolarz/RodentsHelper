package com.example.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;

import com.example.rodentshelper.Notifications.NotificationsModel;
import com.example.rodentshelper.ROOM.Notes.NotesModel;
import com.example.rodentshelper.ROOM.Weights.WeightModel;
import com.example.rodentshelper.ROOM._MTM._RodentWeight.RodentWithWeights;

import java.sql.Date;
import java.util.List;

@Dao
public interface DAONotifications {

    @Insert
    void insertRecordNotification(NotificationsModel Notification);


    @Query("SELECT * FROM notification WHERE notification_type = 'visit'")
    List<NotificationsModel> getAllNotificationsVisit();



    @Query("SELECT id_notification FROM Notification WHERE notification_type = 'weight'")
    Integer getIdFromNotificationWeight();

    @Query("SELECT MIN(id_notification) FROM Notification WHERE notification_type = 'feeding'")
    Integer getFirstIdFromNotificationFeeding();
    @Query("SELECT MAX(id_notification) FROM Notification WHERE notification_type = 'feeding'")
    Integer getLastIdFromNotificationFeeding();

    @Query("SELECT id_notification FROM Notification WHERE notification_type = 'visit' AND " +
            "id_rodent = :id_rodent")
    Integer getIdFromNotificationVisit(Integer id_rodent);


    @Query("SELECT hour FROM Notification WHERE notification_type = 'weight'")
    Integer getHourFromNotificationWeight();

    @Query("SELECT minute FROM Notification WHERE notification_type = 'weight'")
    Integer getMinuteFromNotificationWeight();

    @Query("SELECT periodicity FROM Notification WHERE notification_type = 'weight'")
    String getPeriodicityFromNotificationWeight();

    @TypeConverters(Converters.class)
    @Query("SELECT unix_timestamps FROM Notification WHERE notification_type = 'weight'")
    Long getUnixTimestampsFromNotificationWeight();

    @TypeConverters(Converters.class)
    @Query("SELECT next_notification_time FROM Notification WHERE notification_type = 'weight'")
    Long getNextNotificationTimeWeight();

    @TypeConverters(Converters.class)
    @Query("UPDATE Notification SET unix_timestamps = :unix_timestamps WHERE notification_type = 'weight'")
    void updateUnixTimestampWeight(Long unix_timestamps);

    @TypeConverters(Converters.class)
    @Query("UPDATE Notification SET next_notification_time = :next_notification_time WHERE notification_type = 'weight'")
    void updateNextNotificationTimeWeight(Long next_notification_time);






    @Query("SELECT hour FROM Notification WHERE notification_type = 'feeding' AND " +
            "id_notification = :id_notification")
    Integer getHourFromNotificationFeeding(Integer id_notification);

    @Query("SELECT minute FROM Notification WHERE notification_type = 'feeding' AND " +
            "id_notification = :id_notification")
    Integer getMinuteFromNotificationFeeding(Integer id_notification);

    @TypeConverters(Converters.class)
    @Query("SELECT unix_timestamps FROM Notification WHERE notification_type = 'feeding' AND " +
            "id_notification = :id_notification")
    Long getUnixTimestampsFromNotificationFeeding(Integer id_notification);

    @TypeConverters(Converters.class)
    @Query("SELECT MIN(next_notification_time) FROM Notification WHERE notification_type = 'feeding'")
    Long getNextNotificationTimeFeeding();

    @TypeConverters(Converters.class)
    @Query("UPDATE Notification SET unix_timestamps = :unix_timestamps WHERE notification_type = 'feeding' AND " +
            "id_notification = :id_notification")
    void updateUnixTimestampFeeding(Long unix_timestamps, Integer id_notification);

    @TypeConverters(Converters.class)
    @Query("UPDATE Notification SET next_notification_time = :next_notification_time WHERE notification_type = 'feeding' AND " +
            "id_notification = :id_notification")
    void updateNextNotificationTimeFeeding(Long next_notification_time, Integer id_notification);



    @Query("SELECT hour FROM Notification WHERE notification_type = 'visit' AND " +
            "id_notification = :id_notification AND id_rodent = :id_rodent")
    Integer getHourFromNotificationVisit(Integer id_notification, Integer id_rodent);

    @Query("SELECT minute FROM Notification WHERE notification_type = 'visit' AND " +
            "id_notification = :id_notification AND id_rodent = :id_rodent")
    Integer getMinuteFromNotificationVisit(Integer id_notification, Integer id_rodent);

    @TypeConverters(Converters.class)
    @Query("SELECT unix_timestamps FROM Notification WHERE notification_type = 'visit' AND " +
            "id_notification = :id_notification")
    Long getUnixTimestampFromNotificationVisit(Integer id_notification);

    @TypeConverters(Converters.class)
    @Query("SELECT next_notification_time FROM Notification WHERE notification_type = 'visit' AND " +
            "id_notification = :id_notification")
    Long getNextNotificationTimeVisit(Integer id_notification);

    @TypeConverters(Converters.class)
    @Query("UPDATE Notification SET unix_timestamps = :unix_timestamps WHERE notification_type = 'visit' AND " +
            "id_notification = :id_notification")
    void updateUnixTimestampVisit(Long unix_timestamps, Integer id_notification);

    @TypeConverters(Converters.class)
    @Query("UPDATE Notification SET next_notification_time = :next_notification_time WHERE notification_type = 'visit' AND " +
            "id_notification = :id_notification")
    void updateNextNotificationTimeVisit(Long next_notification_time, Integer id_notification);

    @Query("SELECT MAX(id_notification) FROM Notification WHERE notification_type = 'visit'")
    Integer getLastIdFromNotificationVisit();





    /*@Query("SELECT hour FROM Notification WHERE notification_type = 'visit' AND id_rodent = :id_rodent")
    List<NotificationsModel> getHourFromNotificationVisit(Integer id_rodent);

    @Query("SELECT minute FROM Notification WHERE notification_type = 'visit' AND id_rodent = :id_rodent")
    List<NotificationsModel> getMinuteFromNotificationVisit(Integer id_rodent);*/



    @Query("DELETE FROM Notification WHERE id_rodent = :id_rodent")
    void deleteNotificationByRodentId(Integer id_rodent);

    @Query("DELETE FROM Notification WHERE notification_type = 'weight'")
    void deleteNotificationWeight();

    @Query("DELETE FROM Notification WHERE notification_type = 'feeding'")
    void deleteNotificationFeeding();

    @Query("DELETE FROM Notification WHERE notification_type = 'visit' AND id_notification = :id_notification")
    void deleteNotificationVisit(Integer id_notification);

}


