package com.android.rodentshelper.ROOM._MTM._RodentMed;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        primaryKeys = {"id_rodent", "id_medicament"},
        tableName = "rodents_medicaments")
public class RodentMedModel {

    @NonNull
    private Integer id_rodent;
    @NonNull
    private Integer id_medicament;


    public RodentMedModel(@NonNull Integer id_rodent, @NonNull Integer id_medicament) {
        this.id_rodent = id_rodent;
        this.id_medicament = id_medicament;
    }


    @NonNull
    public Integer getId_rodent() {
        return id_rodent;
    }

    public void setId_rodent(@NonNull Integer id_rodent) {
        this.id_rodent = id_rodent;
    }

    @NonNull
    public Integer getId_medicament() {
        return id_medicament;
    }

    public void setId_medicament(@NonNull Integer id_medicament) {
        this.id_medicament = id_medicament;
    }
}
