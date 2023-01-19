package com.example.rodentshelper.Encyclopedia.Treats;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Treats")
public class TreatsModel {

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

    @ColumnInfo(name = "is_healthy")
    private Boolean is_healthy;


    public TreatsModel(Integer id_animal, String name, String description, byte[] image, Boolean is_healthy) {
        this.id_animal = id_animal;
        this.name = name;
        this.description = description;
        this.image = image;
        this.is_healthy = is_healthy;
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

    public Boolean getIs_healthy() {
        return is_healthy;
    }

    public void setIs_healthy(Boolean is_healthy) {
        this.is_healthy = is_healthy;
    }
}
