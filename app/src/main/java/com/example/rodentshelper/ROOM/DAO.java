package com.example.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.rodentshelper.DatabaseManagement.CloudAccountModel;
import com.example.rodentshelper.DatabaseManagement.DatabaseManagementModel;
import com.example.rodentshelper.ROOM.Medicaments.MedicamentModel;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;

import java.sql.Date;
import java.util.List;

@Dao
public interface DAO {



    @Insert
    void insertRecordDBManagement(DatabaseManagementModel DatabaseManagement);

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

    @Query ("DELETE FROM CloudAccount")
    void DeleteCloudAccount();




   /* @Query ("SELECT rodents.name\n" +
            "FROM rodents\n" +
            "JOIN vets ON rodents_vets.id_vet = vets.id\n" +
            "JOIN rodents_vets ON rodents.id = rodents_vets.id_rodent\n" +
            "WHERE vets.id = :id")
    List<String> getAllRodentsVets(Integer id);*/



   /* @Query ("DELETE FROM  rodents_vets WHERE id_vet = :id")
    void DeleteAllRodentsVetsByVet(Integer id);

    @Query ("DELETE FROM rodents_vets WHERE id_rodent = :id")
    void DeleteAllRodentsVetsByRodent(Integer id);*/

    /*************/
    /** RODENTS **/
    /*************/





    /**************/
    /**** VETS ****/
    /**************/





    /*****************/
    /** MEDICAMENTS **/
    /*****************/




    /************/
    /** VISITS **/
    /************/



}


