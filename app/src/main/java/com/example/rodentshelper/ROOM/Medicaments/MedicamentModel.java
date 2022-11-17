package com.example.rodentshelper.ROOM.Medicaments;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.rodentshelper.ROOM.Converters;

import java.sql.Date;

@Entity(tableName = "medicaments")
public class MedicamentModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "id_vet")
    private Integer id_vet;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "periodicity")
    private String periodicity;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "date_start")
    private Date date_start;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "date_end")
    private Date date_end;


    public MedicamentModel(Integer id_vet, String name, String description, String periodicity, Date date_start, Date date_end) {
        this.id_vet = id_vet;
        this.name = name;
        this.description = description;
        this.periodicity = periodicity;
        this.date_start = date_start;
        this.date_end = date_end;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_vet() {
        return id_vet;
    }

    public void setId_vet(Integer id_vet) {
        this.id_vet = id_vet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }
}
