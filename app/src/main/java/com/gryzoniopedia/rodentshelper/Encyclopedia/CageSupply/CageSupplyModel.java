package com.gryzoniopedia.rodentshelper.Encyclopedia.CageSupply;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CageSupply")
public class CageSupplyModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "id_animal")
    private Integer id_animal;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @ColumnInfo(name = "is_good")
    private Boolean is_good;


    public CageSupplyModel(Integer id_animal, String name, String description, byte[] image, Boolean is_good) {
        this.id_animal = id_animal;
        this.name = name;
        this.description = description;
        this.image = image;
        this.is_good = is_good;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Boolean getIs_good() {
        return is_good;
    }

    public void setIs_good(Boolean is_good) {
        this.is_good = is_good;
    }
}
