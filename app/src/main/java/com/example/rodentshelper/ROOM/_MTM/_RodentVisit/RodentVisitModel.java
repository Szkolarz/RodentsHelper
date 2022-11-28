package com.example.rodentshelper.ROOM._MTM._RodentVisit;

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


    public RodentVisitModel(Integer id_rodent, Integer id_visit) {
        this.id_rodent = id_rodent;
        this.id_visit = id_visit;
    }



    public Integer getId_rodent() {
        return id_rodent;
    }

    public void setId_rodent(Integer id_rodent) {
        this.id_rodent = id_rodent;
    }

    public Integer getId_visit() {
        return id_visit;
    }

    public void setId_visit(Integer id_visit) {
        this.id_visit = id_visit;
    }
}
