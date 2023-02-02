package com.gryzoniopedia.rodentshelper.ROOM.Unused;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.gryzoniopedia.rodentshelper.ROOM.Converters;

import java.sql.Date;

@Entity(tableName = "Sounds")
public class SoundsModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id_sound;
    @ColumnInfo(name = "id_rodent")
    private Integer id_rodent;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;


    public SoundsModel(Integer id_rodent, String description, byte[] image) {
        this.id_rodent = id_rodent;
        this.description = description;
        this.image = image;
    }

    public Integer getId_sound() {
        return id_sound;
    }

    public void setId_sound(Integer id_sound) {
        this.id_sound = id_sound;
    }

    public Integer getId_rodent() {
        return id_rodent;
    }

    public void setId_rodent(Integer id_rodent) {
        this.id_rodent = id_rodent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
