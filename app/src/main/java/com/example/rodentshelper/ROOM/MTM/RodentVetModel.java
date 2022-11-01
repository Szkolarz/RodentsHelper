package com.example.rodentshelper.ROOM.MTM;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rodents_vets")
public class RodentVetModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "id_rodent")
    private Integer id_rodent;
    @ColumnInfo(name = "id_vet")
    private Integer id_vet;



    public RodentVetModel(Integer id_rodent, Integer id_vet) {
        this.id_rodent = id_rodent;
        this.id_vet = id_vet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
