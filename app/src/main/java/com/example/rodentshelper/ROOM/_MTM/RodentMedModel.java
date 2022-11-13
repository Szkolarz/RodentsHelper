package com.example.rodentshelper.ROOM._MTM;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rodents_medicaments")
public class RodentMedModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "id_rodent")
    private Integer id_rodent;
    @ColumnInfo(name = "id_med")
    private Integer id_med;



    public RodentMedModel(Integer id_rodent, Integer id_med) {
        this.id_rodent = id_rodent;
        this.id_med = id_med;
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

    public Integer getId_med() {
        return id_med;
    }

    public void setId_med(Integer id_med) {
        this.id_med = id_med;
    }
}
