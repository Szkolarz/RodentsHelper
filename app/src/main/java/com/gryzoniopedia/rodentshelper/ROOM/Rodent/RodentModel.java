package com.gryzoniopedia.rodentshelper.ROOM.Rodent;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.gryzoniopedia.rodentshelper.ROOM.Converters;

import java.sql.Date;

@Entity(tableName = "rodents")
public class RodentModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id_rodent;
    @ColumnInfo(name = "id_animal")
    private Integer id_animal;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "gender")
    private String gender;
    @TypeConverters(Converters.class)
    @ColumnInfo(name = "birth")
    private Date birth;
    @ColumnInfo(name = "fur")
    private String fur;
    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;


    public RodentModel(Integer id_animal, String name, String gender, Date birth, String fur, String notes, byte[] image) {
        this.id_animal = id_animal;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.fur = fur;
        this.notes = notes;
        this.image = image;

    }

    public Integer getId_rodent() {
        return id_rodent;
    }

    public void setId_rodent(Integer id_rodent) {
        this.id_rodent = id_rodent;
    }


    public Integer getId() {
        return id_rodent;
    }

    public void setId(Integer id) {
        this.id_rodent = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId_animal() {
        return id_animal;
    }

    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getFur() {
        return fur;
    }

    public void setFur(String fur) {
        this.fur = fur;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
