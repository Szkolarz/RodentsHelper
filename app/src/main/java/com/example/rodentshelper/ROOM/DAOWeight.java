package com.example.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;


import com.example.rodentshelper.ROOM.Weights.WeightModel;
import com.example.rodentshelper.ROOM._MTM._RodentWeight.RodentWithWeights;


import java.sql.Date;
import java.util.List;

@Dao
public interface DAOWeight {


    @Insert
    void insertRecordWeight(WeightModel weight);





    @Query("SELECT * FROM weight WHERE id_rodent = :id_rodent ORDER BY date ASC")
    List<WeightModel> getAllWeights(Integer id_rodent);



    @TypeConverters(Converters.class)
    @Query("UPDATE weight SET weight = :weight, date = :date WHERE id_weight = :id")
    void updateWeightById(Integer id, Integer weight, Date date);


    @Query ("SELECT rodents.id_rodent FROM rodents WHERE rodents.id_rodent = :id")
    Integer getIdofRodent(Integer id);


    //** *//
    @TypeConverters(Converters.class)
    @Query ("SELECT EXISTS (SELECT 1 FROM weight WHERE id_rodent = :id_rodent AND date = :date)")
    Boolean isDateTheSame(Integer id_rodent, Date date);



    @Transaction
    @Query("SELECT * FROM weight WHERE id_rodent = :id_rodent ORDER BY date DESC")
    public List<RodentWithWeights> getRodentWithWeights(Integer id_rodent);

    @Transaction
    @Query("SELECT * FROM weight WHERE id_rodent = :id_rodent ORDER BY date ASC")
    public List<RodentWithWeights> getRodentWithWeightsASC(Integer id_rodent);

    @Transaction
    @Query("SELECT * FROM weight WHERE id_rodent = :id_rodent ORDER BY date DESC LIMIT 1")
    public List<RodentWithWeights> getLastWeightByRodentId(Integer id_rodent);




    @Query("DELETE FROM weight WHERE id_weight = :id")
    void deleteWeightById(Integer id);




}


