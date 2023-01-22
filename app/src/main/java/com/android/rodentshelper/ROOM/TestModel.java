package com.android.rodentshelper.ROOM;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;

@Entity(tableName = "MedicalTests")
public class TestModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id_medicalTest;

    @ColumnInfo(name = "id_rodent")
    private Integer id_rodent;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "result")
    private String result;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "test_date")
    private Date test_date;

    @ColumnInfo(name = "notes")
    private String notes;


    public TestModel(Integer id_rodent, String name, String result, Date test_date, String notes) {
        this.id_rodent = id_rodent;
        this.name = name;
        this.result = result;
        this.test_date = test_date;
        this.notes = notes;
    }


    public Integer getId_medicalTest() {
        return id_medicalTest;
    }

    public void setId_medicalTest(Integer id_medicalTest) {
        this.id_medicalTest = id_medicalTest;
    }

    public Integer getId_rodent() {
        return id_rodent;
    }

    public void setId_rodent(Integer id_rodent) {
        this.id_rodent = id_rodent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getTest_date() {
        return test_date;
    }

    public void setTest_date(Date test_date) {
        this.test_date = test_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
