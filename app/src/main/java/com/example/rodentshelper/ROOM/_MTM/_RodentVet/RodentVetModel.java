package com.example.rodentshelper.ROOM._MTM._RodentVet;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Vet.VetModel;

import java.util.List;

@Entity(
        primaryKeys = {"id_rodent", "id_vet"})
public class RodentVetModel {

    @NonNull
    private Integer id_rodent;
    @NonNull
    private Integer id_vet;



    public RodentVetModel(Integer id_rodent, Integer id_vet) {
        this.id_rodent = id_rodent;
        this.id_vet = id_vet;
    }



    public Integer getId_rodent() {
        return id_rodent;
    }

    public void setId_rodent(Integer id_rodent) {
        this.id_rodent = id_rodent;
    }

    public Integer getId_vet() {
        return id_vet;
    }

    public void setId_vet(Integer id_vet) {
        this.id_vet = id_vet;
    }
}

