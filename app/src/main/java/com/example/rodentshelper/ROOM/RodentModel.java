package com.example.rodentshelper.ROOM;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;

@Entity(tableName = "rodents")
public class RodentModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "id_animal")
    private int id_animal;

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



    public RodentModel(Integer id_animal, String name, String gender, Date birth, String fur, String notes) {
        this.id_animal = id_animal;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.fur = fur;
        this.notes = notes;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_animal() {
        return id_animal;
    }

    public void setId_animal(int id_animal) {
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
}
