package com.example.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.sql.Date;
import java.util.List;

@Dao
public interface DAO {

    @Insert
    void insertrecord(RodentModel users);

    @Query("SELECT EXISTS(SELECT * FROM rodents WHERE id = :id)")
    Boolean is_exist(int id);


    @Query("SELECT * FROM rodents")
    List<RodentModel> getAllRodents();

    @Query("DELETE FROM rodents WHERE id = :id")
    void deleteRodentById(int id);


    @TypeConverters(Converters.class)
    @Query("UPDATE rodents SET id_animal = :id_animal, name = :name, gender = :gender, birth = :date, fur = :fur, notes = :notes WHERE id = :id")
    void updateRodentById(int id, int id_animal, String name, String gender, Date date, String fur, String notes);
}
