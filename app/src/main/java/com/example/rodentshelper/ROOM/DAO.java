package com.example.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.rodentshelper.Medicaments.MedicamentModel;
import com.example.rodentshelper.ROOM.MTM.RodentMedModel;
import com.example.rodentshelper.ROOM.MTM.RodentVetModel;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Vet.VetModel;

import java.sql.Date;
import java.util.List;

@Dao
public interface DAO {


    @Insert
    void insertRecordRodentMed(RodentMedModel rodents_medicaments);


    @Query ("SELECT rodents.name\n" +
            "FROM rodents\n" +
            "JOIN medicaments ON rodents_medicaments.id_med = medicaments.id\n" +
            "JOIN rodents_medicaments ON rodents.id = rodents_medicaments.id_rodent\n" +
            "WHERE medicaments.id = :id")
    List<String> getAllRodentsMeds(int id);

    @Query ("DELETE FROM rodents_medicaments WHERE id_med = :id")
    void DeleteAllRodentsMedsByMed(int id);

    @Query ("DELETE FROM rodents_medicaments WHERE id_rodent = :id")
    void DeleteAllRodentsMedsByRodent(int id);



    @Insert
    void insertRecordRodentVet(RodentVetModel rodents_vets);

    //@Query("SELECT name FROM rodents where id = :id")
    //List<RodentModel> getAllRodentsVets(int id);

    @Query ("SELECT rodents.name\n" +
            "FROM rodents\n" +
            "JOIN vets ON rodents_vets.id_vet = vets.id\n" +
            "JOIN rodents_vets ON rodents.id = rodents_vets.id_rodent\n" +
            "WHERE vets.id = :id")
    List<String> getAllRodentsVets(int id);

    @Query ("DELETE FROM  rodents_vets WHERE id_vet = :id")
    void DeleteAllRodentsVetsByVet(int id);

    @Query ("DELETE FROM rodents_vets WHERE id_rodent = :id")
    void DeleteAllRodentsVetsByRodent(int id);

    /*************/
    /** RODENTS **/
    /*************/

    @Insert
    void insertRecordRodent(RodentModel rodents);

    /*@Query("SELECT EXISTS(SELECT * FROM rodents WHERE id = :id)")
    Boolean is_exist(int id);*/

    @Query("SELECT * FROM rodents ORDER BY id ASC")
    List<RodentModel> getAllRodents();

    @Query("SELECT name FROM rodents ORDER BY id ASC")
    List<String> getAllNameRodents();

    //@Query("SELECT id FROM rodents ORDER BY id ASC")
    //List<RodentModel> getAllIdRodents();

    @Query("DELETE FROM rodents WHERE id = :id")
    void deleteRodentById(int id);

    @TypeConverters(Converters.class)
    @Query("UPDATE rodents SET id_animal = :id_animal, name = :name, gender = :gender, birth = :date, fur = :fur, notes = :notes, image = :image WHERE id = :id")
    void updateRodentById(int id, int id_animal, String name, String gender, Date date, String fur, String notes, byte[] image);

    @Query("SELECT image FROM rodents WHERE id = :id")
    byte[] getImageById(int id);


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
    @Query("UPDATE vets SET name = :name, address = :address, phone_number = :phone, notes = :notes WHERE id = :id")
    void updateVetById(int id, String name, String address, String phone, String notes);

    @Query("SELECT id FROM vets WHERE id = (SELECT MAX(id) FROM vets)")
    List<Integer> getLastIdVet();



    /*****************/
    /** MEDICAMENTS **/
    /*****************/

    @Insert
    void insertRecordMed(MedicamentModel medicaments);

    @Query("SELECT * FROM medicaments")
    List<MedicamentModel> getAllMedicaments();

    @Query("DELETE FROM medicaments WHERE id = :id")
    void deleteMedById(int id);

    @TypeConverters(Converters.class)
    @Query("UPDATE medicaments SET id_vet = :id_vet, name = :name, description = :description, periodicity = :periodicity, date_start = :date_start, date_end = :date_end WHERE id = :id")
    void updateMedById(int id, int id_vet, String name, String description, String periodicity, Date date_start, Date date_end);

    @Query("SELECT id FROM medicaments WHERE id = (SELECT MAX(id) FROM medicaments)")
    List<Integer> getLastIdMed();

}


