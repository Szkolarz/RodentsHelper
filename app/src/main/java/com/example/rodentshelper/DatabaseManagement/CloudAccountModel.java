package com.example.rodentshelper.DatabaseManagement;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.rodentshelper.ROOM.Converters;

import java.sql.Date;

@Entity(tableName = "CloudAccount")
public class CloudAccountModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "login")
    private String  login;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "export_date")
    private Date export_date;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "import_date")
    private Date import_date;


    public CloudAccountModel(String login, Date export_date, Date import_date) {
        this.login = login;
        this.export_date = export_date;
        this.import_date = import_date;
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

    public Date getExport_date() {
        return export_date;
    }

    public void setExport_date(Date export_date) {
        this.export_date = export_date;
    }

    public Date getImport_date() {
        return import_date;
    }

    public void setImport_date(Date import_date) {
        this.import_date = import_date;
    }
}
