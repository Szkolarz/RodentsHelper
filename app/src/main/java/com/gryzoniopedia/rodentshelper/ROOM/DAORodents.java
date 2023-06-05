package com.gryzoniopedia.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;

import com.gryzoniopedia.rodentshelper.ROOM.Rodent.RodentModel;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentNotes.RodentWithNotes;

import java.sql.Date;
import java.util.List;

@Dao
public interface DAORodents {

    @Insert
    void insertRecordRodent(RodentModel rodents);

    /*@Query("SELECT EXISTS(SELECT * FROM rodents WHERE id = :id)")
    Boolean is_exist(Integer id);*/

    @Transaction
    @Query("SELECT * FROM rodents WHERE id_animal = :id_animal ORDER BY id_rodent DESC")
    List<RodentModel> getAllRodentsWhereIdAnimalDESC(Integer id_animal);

    @Query("SELECT * FROM rodents WHERE id_animal = :id_animal ORDER BY id_rodent ASC")
    List<RodentModel> getAllRodentsWhereIdAnimalASC(Integer id_animal);

    @Query("SELECT * FROM rodents WHERE id_animal = :id_animal ORDER BY name DESC")
    List<RodentModel> getAllRodentsWhereIdAnimalOrderByNameDESC(Integer id_animal);

    @Query("SELECT * FROM rodents WHERE id_animal = :id_animal ORDER BY name ASC")
    List<RodentModel> getAllRodentsWhereIdAnimalOrderByNameASC(Integer id_animal);

    @Query("SELECT * FROM rodents ORDER BY id_rodent ASC")
    List<RodentModel> getAllRodentsTEST();

    @Query("SELECT name FROM rodents ORDER BY id_rodent ASC")
    List<String> getAllNameRodents();

    //@Query("SELECT id FROM rodents ORDER BY id ASC")
    //List<RodentModel> getAllIdRodents();

    @Query("DELETE FROM rodents WHERE id_rodent = :id")
    void deleteRodentById(Integer id);

    @TypeConverters(Converters.class)
    @Query("UPDATE rodents SET id_animal = :id_animal, name = :name, gender = :gender, birth = :date, fur = :fur, notes = :notes, image = :image WHERE id_rodent = :id")
    void updateRodentById(Integer id, Integer id_animal, String name, String gender, Date date, String fur, String notes, byte[] image);

    @Query("SELECT image FROM rodents WHERE id_rodent = :id")
    byte[] getImageById(Integer id);

    @Query("UPDATE rodents SET image = NULL WHERE id_rodent = :id_rodent")
    void setImageNullById(Integer id_rodent);



/*    @Query ("SELECT vets.id, vets.name, vets.address, vets.phone_number, vets.notes FROM vets\n" +
            "LEFT JOIN rodents  ON (rodents_vets.id_vet = vets.id)\n" +
            "LEFT JOIN rodents_vets ON (rodents.id = rodents_vets.id_rodent)\n" +
            "WHERE rodents.id = :id")
    List<VetModel> getAllVetsByRodentId(Integer id);*/




}


