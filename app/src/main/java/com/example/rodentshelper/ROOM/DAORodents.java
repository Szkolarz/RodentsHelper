package com.example.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.rodentshelper.ROOM.Rodent.RodentModel;

import java.sql.Date;
import java.util.List;

@Dao
public interface DAORodents {

    @Insert
    void insertRecordRodent(RodentModel rodents);

    /*@Query("SELECT EXISTS(SELECT * FROM rodents WHERE id = :id)")
    Boolean is_exist(Integer id);*/

    @Query("SELECT * FROM rodents WHERE id_animal = :id_animal ORDER BY id_rodent ASC")
    List<RodentModel> getAllRodents(Integer id_animal);

    @Query("SELECT name1 FROM rodents ORDER BY id_rodent ASC")
    List<String> getAllNameRodents();

    //@Query("SELECT id FROM rodents ORDER BY id ASC")
    //List<RodentModel> getAllIdRodents();

    @Query("DELETE FROM rodents WHERE id_rodent = :id")
    void deleteRodentById(Integer id);

    @TypeConverters(Converters.class)
    @Query("UPDATE rodents SET id_animal = :id_animal, name1 = :name, gender = :gender, birth = :date, fur = :fur, notes1 = :notes, image = :image WHERE id_rodent = :id")
    void updateRodentById(Integer id, Integer id_animal, String name, String gender, Date date, String fur, String notes, byte[] image);

    @Query("SELECT image FROM rodents WHERE id_rodent = :id")
    byte[] getImageById(Integer id);

/*    @Query ("SELECT vets.id, vets.name, vets.address, vets.phone_number, vets.notes FROM vets\n" +
            "LEFT JOIN rodents  ON (rodents_vets.id_vet = vets.id)\n" +
            "LEFT JOIN rodents_vets ON (rodents.id = rodents_vets.id_rodent)\n" +
            "WHERE rodents.id = :id")
    List<VetModel> getAllVetsByRodentId(Integer id);*/




}


