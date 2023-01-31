package com.gryzoniopedia.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gryzoniopedia.rodentshelper.Encyclopedia.CageSupply.CageSupplyModel;
import com.gryzoniopedia.rodentshelper.Encyclopedia.Diseases.DiseasesModel;
import com.gryzoniopedia.rodentshelper.Encyclopedia.General.GeneralModel;
import com.gryzoniopedia.rodentshelper.Encyclopedia.Treats.TreatsModel;
import com.gryzoniopedia.rodentshelper.Encyclopedia.Version.VersionModel;

import java.util.List;

@Dao
public interface DAOEncyclopedia {


    /*************/
    /** Version **/
    /*************/

    @Insert
    void insertRecordVersion(VersionModel Version);

    @Query("SELECT code FROM Version WHERE id_animal = :id_animal")
    String getVersionCode(Integer id_animal);


    @Query("SELECT COUNT(id_animal) FROM Treats WHERE id_animal = :id_animal")
    Integer getTreatsCountTest(Integer id_animal);

    @Query("DELETE FROM Version")
    void deleteVersion();





    /******************/
    /**  Treats **/
    /******************/

    @Insert
    void insertRecordTreats(TreatsModel Treats);

    @Query("SELECT * FROM Treats WHERE id_animal = :id_animal AND is_healthy = 1 " +
            "EXCEPT SELECT * FROM Treats WHERE name = 'Info' ORDER BY id ASC")
    List<TreatsModel> getAllTreatsHealthy(Integer id_animal);

    @Query("SELECT * FROM Treats WHERE id_animal = :id_animal AND is_healthy = 0 " +
            "EXCEPT SELECT * FROM Treats WHERE name = 'Info' ORDER BY id ASC")
    List<TreatsModel> getAllTreatsNotHealthy(Integer id_animal);

    @Query("SELECT * FROM Treats WHERE name ='Info' AND id_animal = :id_animal")
    List<TreatsModel> getTreatsAdditionalInfo(Integer id_animal);

    @Query("DELETE FROM Treats WHERE id_animal = :id_animal")
    void deleteTreats(Integer id_animal);






    /****************/
    /** CageSupply **/
    /****************/

    @Insert
    void insertRecordCageSupply(CageSupplyModel CageSupply);

    @Query("SELECT * FROM CageSupply WHERE id_animal = :id_animal AND is_good = 1 " +
            "EXCEPT SELECT * FROM CageSupply WHERE name = 'Info' ORDER BY id ASC")
    List<CageSupplyModel> getAllCageSuppliesGood(Integer id_animal);

    @Query("SELECT * FROM CageSupply WHERE id_animal = :id_animal AND is_good = 0 " +
            "EXCEPT SELECT * FROM CageSupply WHERE name = 'Info' ORDER BY id ASC")
    List<CageSupplyModel> getAllCageSuppliesNotGood(Integer id_animal);




    @Query("SELECT * FROM CageSupply WHERE name ='Info' AND id_animal = :id_animal")
    List<CageSupplyModel> getCageSupplyAdditionalInfo(Integer id_animal);

    @Query("DELETE FROM CageSupply WHERE id_animal = :id_animal")
    void deleteCageSupply(Integer id_animal);



    /*************/
    /** General **/
    /*************/

    @Insert
    void insertRecordGeneral(GeneralModel General);

    @Query("SELECT * FROM General WHERE id_animal = :id_animal EXCEPT SELECT * FROM General WHERE name = 'Info'" +
            " ORDER BY id ASC")
    List<GeneralModel> getAllGeneral(Integer id_animal);

    @Query("SELECT * FROM General WHERE name ='Info' AND id_animal = :id_animal")
    List<GeneralModel> getGeneralAdditionalInfo(Integer id_animal);

    @Query("DELETE FROM General WHERE id_animal = :id_animal")
    void deleteGeneral(Integer id_animal);



    /**************/
    /** Diseases **/
    /**************/

    @Insert
    void insertRecordDiseases(DiseasesModel Diseases);

    @Query("SELECT * FROM Diseases WHERE id_animal = :id_animal EXCEPT SELECT * FROM Diseases WHERE name = 'Info'" +
            " ORDER BY id ASC")
    List<DiseasesModel> getAllDiseases(Integer id_animal);

    @Query("SELECT * FROM Diseases WHERE name ='Info' AND id_animal = :id_animal")
    List<DiseasesModel> getDiseasesAdditionalInfo(Integer id_animal);

    @Query("DELETE FROM Diseases WHERE id_animal = :id_animal")
    void deleteDiseases(Integer id_animal);




}


