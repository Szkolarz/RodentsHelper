package com.example.rodentshelper;

public class VetsModelClass {

    private Integer id;
    private Integer id_animal;
    private String name;
    private String address;
    private String phone_number;
    private String notes;


    public VetsModelClass(Integer id_animal, String name, String address, String phone_number, String notes) {
        this.id_animal = id_animal;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.notes = notes;
    }

    public VetsModelClass(Integer id, Integer id_animal, String name, String address, String phone_number, String notes) {
        this.id = id;
        this.id_animal = id_animal;
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
