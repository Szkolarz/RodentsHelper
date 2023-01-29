package com.android.rodentshelper.ROOM._MTM._RodentVisit;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        primaryKeys = {"id_rodent", "id_visit"},
        tableName = "rodents_visits")
public class RodentVisitModel {

    @NonNull
    private Integer id_rodent;
    @NonNull
    private Integer id_visit;

    public RodentVisitModel(@NonNull Integer id_rodent, @NonNull Integer id_visit) {
        this.id_rodent = id_rodent;
        this.id_visit = id_visit;
    }

    @NonNull
    public Integer getId_rodent() {
        return id_rodent;
    }
    public void setId_rodent(@NonNull Integer id_rodent) {
        this.id_rodent = id_rodent;
    }
    @NonNull
    public Integer getId_visit() {
        return id_visit;
    }
    public void setId_visit(@NonNull Integer id_visit) {
        this.id_visit = id_visit;
    }
}
