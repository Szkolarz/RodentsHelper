package com.android.rodentshelper.Encyclopedia.Diseases;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Diseases")
public class DiseasesModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "id_animal")
    private Integer id_animal;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;



    public DiseasesModel(Integer id_animal, String name, String description) {
        this.id_animal = id_animal;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_animal() {
        return id_animal;
    }

    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
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


}
