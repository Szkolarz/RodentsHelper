package com.example.rodentshelper.ROOM.Vet;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.rodentshelper.ROOM.Converters;

import java.sql.Date;

@Entity(tableName = "vets")
public class VetModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "phone_number")
    private String phone_number;
    @ColumnInfo(name = "notes")
    private String notes;


    public VetModel(String name, String address, String phone_number, String notes) {
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.notes = notes;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
