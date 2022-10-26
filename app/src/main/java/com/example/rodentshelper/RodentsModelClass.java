package com.example.rodentshelper;

import java.sql.Date;


public class RodentsModelClass {

    private Integer id;
    private Integer id_animal;
    private String name;
    private String gender;
    private Date birth;
    private String fur;
    private String notes;


    public RodentsModelClass() {
    }

    public RodentsModelClass(String name, String gender, Date birth, String fur, String notes) {
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.fur = fur;
        this.notes = notes;
    }

    public RodentsModelClass(Integer id, String name, String gender, String birth, String fur, String notes) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birth = Date.valueOf(birth);
        this.fur = fur;
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
