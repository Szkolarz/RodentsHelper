package com.gryzoniopedia.rodentshelper.Encyclopedia.Version;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Version")
public class VersionModel {

    @PrimaryKey(autoGenerate = false)
    private Integer id_animal;

    @ColumnInfo(name = "code")
    private String code;


    public VersionModel(Integer id_animal, String code) {
        this.id_animal = id_animal;
        this.code = code;
    }

    public Integer getId_animal() {
        return id_animal;
    }

    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
