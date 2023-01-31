package com.gryzoniopedia.rodentshelper.ROOM.Visits;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.gryzoniopedia.rodentshelper.ROOM.Converters;

import java.sql.Date;

@Entity(tableName = "visits")
public class VisitModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id_visit;

    @ColumnInfo(name = "id_vet")
    private Integer id_vet;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "reason")
    private String reason;


    public VisitModel(Integer id_vet, Date date, String time, String reason) {
        this.id_vet = id_vet;
        this.date = date;
        this.time = time;
        this.reason = reason;
    }


    public Integer getId_visit() {
        return id_visit;
    }

    public void setId_visit(Integer id_visit) {
        this.id_visit = id_visit;
    }

    public Integer getId_vet() {
        return id_vet;
    }

    public void setId_vet(Integer id_vet) {
        this.id_vet = id_vet;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
