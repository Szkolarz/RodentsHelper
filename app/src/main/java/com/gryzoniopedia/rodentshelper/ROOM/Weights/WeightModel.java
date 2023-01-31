package com.gryzoniopedia.rodentshelper.ROOM.Weights;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.gryzoniopedia.rodentshelper.ROOM.Converters;

import java.sql.Date;

@Entity(tableName = "weight")
public class WeightModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id_weight;

    @ColumnInfo(name = "id_rodent")
    private Integer id_rodent;

    @ColumnInfo(name = "weight")
    private Integer weight;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "date")
    private Date date;

    public WeightModel(Integer id_rodent, Integer weight, Date date) {
        this.id_rodent = id_rodent;
        this.weight = weight;
        this.date = date;
    }


    public Integer getId_weight() {
        return id_weight;
    }

    public void setId_weight(Integer id_weight) {
        this.id_weight = id_weight;
    }

    public Integer getId_rodent() {
        return id_rodent;
    }

    public void setId_rodent(Integer id_rodent) {
        this.id_rodent = id_rodent;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
