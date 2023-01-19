package com.example.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;

import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Vet.VetModel;
import com.example.rodentshelper.ROOM._MTM._RodentVet.RodentVetModel;
import com.example.rodentshelper.ROOM._MTM._RodentVet.VetWithRodentsCrossRef;

import java.util.List;

@Dao
public interface DAOVets {


    @Insert
    void insertRecordVet(VetModel vets);

    @Query("SELECT * FROM vets")
    List<VetModel> getAllVets();

    @Query("SELECT name FROM vets ORDER BY id_vet ASC")
    List<String> getAllNameVets();

    @Query("DELETE FROM vets WHERE id_vet = :id")
    void deleteVetById(Integer id);

    @TypeConverters(Converters.class)
    @Query("UPDATE vets SET name = :name, address = :address, phone_number = :phone, notes = :notes WHERE id_vet = :id")
    void updateVetById(Integer id, String name, String address, String phone, String notes);

    @Query("SELECT id_vet FROM vets WHERE id_vet = (SELECT MAX(id_vet) FROM vets)")
    List<Integer> getLastIdVet();



    @Insert
    void insertRecordRodentVet(RodentVetModel rodents_vets);

    @Transaction
    @Query("SELECT * FROM vets")
    List<VetWithRodentsCrossRef> getVetsWithRodents();


    @Transaction
    @Query ("SELECT vets.id_vet, vets.name, vets.address, vets.phone_number, vets.notes FROM vets\n" +
            "JOIN rodents  ON (RodentVetModel.id_vet = vets.id_vet)\n" +
            "JOIN RodentVetModel ON (rodents.id_rodent = RodentVetModel.id_rodent)\n" +
            "WHERE rodents.id_rodent = :id")
    List<VetWithRodentsCrossRef> getVetsWithRodentsWhereIdRodent(Integer id);



    /** VISITS **/

    @Query ("SELECT vets.id_vet FROM vets WHERE vets.id_vet = :id")
    Integer getIdofVet(Integer id);

    /** RODENTS **/

    @Query("SELECT id_rodent, name1 FROM rodents where id_rodent = :id")
    List<RodentModel> getAllRodentsVets(Integer id);

    @Query ("DELETE FROM RodentVetModel WHERE id_vet = :id")
    void DeleteAllRodentsVetsByVet(Integer id);

    @Query ("SELECT COUNT(*) FROM vets WHERE id_vet <= :id_vet")
    Integer getRealPositionFromVet(Integer id_vet);



   /* @Query("SELECT name1 FROM rodents WHERE ORDER BY id_rodent ASC")
    List<String> getAllNameRodents();*/

   /* @Transaction
    @Query("SELECT * FROM rodents")
    public List<RodentWithVets> getaaa();*/

}


