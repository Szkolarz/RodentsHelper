package com.gryzoniopedia.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.gryzoniopedia.rodentshelper.DatabaseManagement.CloudAccountModel;

import java.sql.Date;
import java.util.List;

@Dao
public interface DAO {



    @Insert
    void insertRecordCloudAccountData(CloudAccountModel CloudAccount);

    @Query("SELECT * FROM CloudAccount")
    List<CloudAccountModel> getAllCloudAccountData();

    @TypeConverters(Converters.class)
    @Query("UPDATE CloudAccount SET export_date = :export_date WHERE login = :login")
    void updateCloudAccountExportDate(Date export_date, String login);

    @TypeConverters(Converters.class)
    @Query("UPDATE CloudAccount SET import_date = :import_date WHERE login = :login")
    void updateCloudAccountImportDate(Date import_date, String login);

    @TypeConverters(Converters.class)
    @Query("UPDATE CloudAccount SET import_date = null")
    void updateCloudAccountImportDateToNull();

    @Query ("DELETE FROM CloudAccount")
    void DeleteCloudAccount();



}


