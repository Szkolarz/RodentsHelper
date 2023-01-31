package com.gryzoniopedia.rodentshelper.DatabaseManagement;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "DatabaseManagement")
public class DatabaseManagementModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "login")
    private String login;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] file;


    public DatabaseManagementModel(String login, byte[] file) {
        this.login = login;
        this.file = file;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
