package com.example.rodentshelper;

public class RodentsModelClass {

    Integer id;
    String name;
    String notes;

    public RodentsModelClass(String name, String notes) {
        this.name = name;
        this.notes = notes;
    }

    public RodentsModelClass(Integer id, String name, String notes) {
        this.id = id;
        this.name = name;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
