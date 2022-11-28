package com.example.rodentshelper.ROOM;

import androidx.room.Dao;

@Dao
public interface DAO {


    /** to delete **/



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


