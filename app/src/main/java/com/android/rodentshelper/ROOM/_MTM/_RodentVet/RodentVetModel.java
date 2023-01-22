package com.android.rodentshelper.ROOM._MTM._RodentVet;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        primaryKeys = {"id_rodent", "id_vet"},
        tableName = "rodents_vets")

public class RodentVetModel {

    @NonNull
    private Integer id_rodent;
    @NonNull
    private Integer id_vet;


    public RodentVetModel(@NonNull Integer id_rodent, @NonNull Integer id_vet) {
        this.id_rodent = id_rodent;
        this.id_vet = id_vet;
    }


    @NonNull
    public Integer getId_rodent() {
        return id_rodent;
    }

    public void setId_rodent(@NonNull Integer id_rodent) {
        this.id_rodent = id_rodent;
    }

    @NonNull
    public Integer getId_vet() {
        return id_vet;
    }

    public void setId_vet(@NonNull Integer id_vet) {
        this.id_vet = id_vet;
    }
}

