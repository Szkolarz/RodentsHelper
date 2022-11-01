package com.example.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Vet.VetModel;

import java.sql.Date;
import java.util.List;

@Dao
public interface DAO {



    @Query("SELECT rodents.name FROM rodents JOIN vets ON vets.id_rodent = rodents.id WHERE vets.id = :id")
    List<String> getNameRelations_VetAndRodent(int id);



    /*************/
    /** RODENTS **/
    /*************/

    @Insert
    void insertRecordRodent(RodentModel rodents);

    /*@Query("SELECT EXISTS(SELECT * FROM rodents WHERE id = :id)")
    Boolean is_exist(int id);*/

    @Query("SELECT * FROM rodents")
    List<RodentModel> getAllRodents();

    @Query("DELETE FROM rodents WHERE id = :id")
    void deleteRodentById(int id);

    @TypeConverters(Converters.class)
    @Query("UPDATE rodents SET id_animal = :id_animal, name = :name, gender = :gender, birth = :date, fur = :fur, notes = :notes WHERE id = :id")
    void updateRodentById(int id, int id_animal, String name, String gender, Date date, String fur, String notes);



    /**************/
    /**** VETS ****/
    /**************/

    @Insert
    void insertRecordVet(VetModel vets);

    @Query("SELECT * FROM vets")
    List<VetModel> getAllVets();

    @Query("DELETE FROM vets WHERE id = :id")
    void deleteVetById(int id);

    @TypeConverters(Converters.class)
    @Query("UPDATE vets SET id_rodent = :id_rodent, name = :name, address = :address, phone_number = :phone, notes = :notes WHERE id = :id")
    void updateVetById(int id, int id_rodent, String name, String address, String phone, String notes);
}


