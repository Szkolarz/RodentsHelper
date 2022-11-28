package com.example.rodentshelper.ROOM._MTM._RodentMed;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(
        primaryKeys = {"id_rodent", "id_medicament"},
        tableName = "rodents_medicaments")
public class RodentMedModel {

    @NonNull
    private Integer id_rodent;
    @NonNull
    private Integer id_medicament;



    public RodentMedModel(Integer id_rodent, Integer id_medicament) {
        this.id_rodent = id_rodent;
        this.id_medicament = id_medicament;
    }



    public Integer getId_rodent() {
        return id_rodent;
    }

    public void setId_rodent(Integer id_rodent) {
        this.id_rodent = id_rodent;
    }

    public Integer getId_medicament() {
        return id_medicament;
    }

    public void setId_medicament(Integer id_medicament) {
        this.id_medicament = id_medicament;
    }
}
