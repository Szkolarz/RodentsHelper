package com.gryzoniopedia.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.gryzoniopedia.rodentshelper.Notifications.Model.NotificationsModel;

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
            "id_visit = :id_visit")
    Integer getIdFromNotificationVisit(Integer id_visit);


    @Query("SELECT hour FROM Notification WHERE notification_type = 'weight'")
    Integer getHourFromNotificationWeight();

    @Query("SELECT minute FROM Notification WHERE notification_type = 'weight'")
    Integer getMinuteFromNotificationWeight();

    @Query("SELECT send_time FROM Notification WHERE notification_type = 'weight'")
    String getSendTimeFromNotificationWeight();

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

    /*@TypeConverters(Converters.class)
    @Query("SELECT MIN(next_notification_time) FROM Notification WHERE notification_type = 'feeding'")
    Long getNextNotificationTimeFeeding();*/

    @TypeConverters(Converters.class)
    @Query("SELECT next_notification_time FROM Notification WHERE notification_type = 'feeding' AND " +
            "id_notification = :id_notification")
    Long getNextNotificationTimeFeeding(Integer id_notification);

    @TypeConverters(Converters.class)
    @Query("UPDATE Notification SET unix_timestamps = :unix_timestamps WHERE notification_type = 'feeding' AND " +
            "id_notification = :id_notification")
    void updateUnixTimestampFeeding(Long unix_timestamps, Integer id_notification);

    @TypeConverters(Converters.class)
    @Query("UPDATE Notification SET next_notification_time = :next_notification_time WHERE notification_type = 'feeding' AND " +
            "id_notification = :id_notification")
    void updateNextNotificationTimeFeeding(Long next_notification_time, Integer id_notification);



    @Query("SELECT hour FROM Notification WHERE notification_type = 'visit' AND " +
            "id_notification = :id_notification AND id_visit = :id_rodent")
    Integer getHourFromNotificationVisit(Integer id_notification, Integer id_rodent);

    @Query("SELECT minute FROM Notification WHERE notification_type = 'visit' AND " +
            "id_notification = :id_notification AND id_visit = :id_rodent")
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

    @Query("SELECT MAX(id_visit) FROM visits")
    Integer getLastIdFromVisit();

    @Query("SELECT id_notification FROM Notification WHERE id_visit = :id_visit")
    Integer checkIfIdVisitExists(Integer id_visit);


    @Query("SELECT send_time FROM Notification WHERE notification_type = 'visit'AND " +
            "id_visit = :id_visit")
    String getSendTimeFromNotificationVisit(Integer id_visit);

    @Query("SELECT id_visit FROM Notification WHERE notification_type = 'visit' AND " +
            "id_visit = :id_visit")
    Integer getIdVisitFromVisit(Integer id_visit);


    @Query ("SELECT COUNT(*) FROM Notification WHERE notification_type = 'visit'")
    Integer getCountNotificationVisit();

    @TypeConverters(Converters.class)
    @Query ("SELECT send_time FROM Notification WHERE notification_type = 'visit' AND " +
            "id_visit = :id_visit")
    String getSendTimeNotificationVisit(Integer id_visit);


    //important change
   /* @Query("UPDATE Notification SET id_notification = (SELECT MAX( id_notification ) + 1 " +
            "FROM Notification WHERE id_visit = :id_visit) WHERE id_visit = :id_visit")
    void updateMaxIdFromNotificationVisit(Integer id_visit);*/
    @Query("SELECT id_notification FROM Notification WHERE id_visit = :id_visit")
    Integer selectIdVisitFromNotificationVisit(Integer id_visit);






    /*@Query("SELECT hour FROM Notification WHERE notification_type = 'visit' AND id_rodent = :id_rodent")
    List<NotificationsModel> getHourFromNotificationVisit(Integer id_rodent);

    @Query("SELECT minute FROM Notification WHERE notification_type = 'visit' AND id_rodent = :id_rodent")
    List<NotificationsModel> getMinuteFromNotificationVisit(Integer id_rodent);*/



    @Query("DELETE FROM Notification WHERE id_visit = :id_visit")
    void deleteNotificationByVisitId(Integer id_visit);

    @Query("DELETE FROM Notification WHERE notification_type = 'weight'")
    void deleteNotificationWeight();

    @Query("DELETE FROM Notification WHERE notification_type = 'feeding'")
    void deleteNotificationFeeding();

    @Query("DELETE FROM Notification WHERE notification_type = 'visit' AND id_notification = :id_notification")
    void deleteNotificationVisit(Integer id_notification);

}


