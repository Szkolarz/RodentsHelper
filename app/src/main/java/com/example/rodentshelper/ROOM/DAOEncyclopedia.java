package com.example.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.rodentshelper.Encyclopedia.CageSupply.CageSupplyModel;
import com.example.rodentshelper.Encyclopedia.Treats.TreatsModel;
import com.example.rodentshelper.ROOM.Medicaments.MedicamentModel;

import java.util.List;

@Dao
public interface DAOEncyclopedia {


    /******************/
    /** 3 Chinchilla **/
    /******************/

    @Insert
    void insertRecordTreats(TreatsModel Treats);

    @Query("SELECT * FROM Treats WHERE id_animal = :id_animal")
    List<TreatsModel> getAllTreats3(Integer id_animal);

    @Query("SELECT * FROM Treats WHERE id_animal = 3 AND is_healthy = 1")
    List<TreatsModel> getAllTreatsTrue3();

    @Query("SELECT * FROM Treats WHERE id_animal = 3 AND is_healthy = 0")
    List<TreatsModel> getAllTreatsFalse3();



    /****************/
    /** CageSupply **/
    /****************/

    @Insert
    void insertRecordCageSupply(CageSupplyModel CageSupply);

    @Query("SELECT * FROM CageSupply WHERE id_animal = :id_animal EXCEPT SELECT * FROM CageSupply WHERE name = 'Info'")
    List<CageSupplyModel> getAllCageSupplies(Integer id_animal);

    @Query("SELECT * FROM CageSupply WHERE name ='Info' AND id_animal = :id_animal")
    List<CageSupplyModel> getCageSupplyAdditionalInfo(Integer id_animal);








}


