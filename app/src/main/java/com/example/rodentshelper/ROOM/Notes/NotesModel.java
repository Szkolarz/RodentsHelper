package com.example.rodentshelper.ROOM.Notes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.rodentshelper.ROOM.Converters;

import java.sql.Date;

@Entity(tableName = "notes")
public class NotesModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id_notes;

    @ColumnInfo(name = "id_rodent")
    private Integer id_rodent;

    @ColumnInfo(name = "topic")
    private String  topic;

    @ColumnInfo(name = "content")
    private String content;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "create_date")
    private Date create_date;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "edit_date")
    private Date edit_date;


    public NotesModel(Integer id_rodent, String topic, String content, Date create_date, Date edit_date) {
        this.id_rodent = id_rodent;
        this.topic = topic;
        this.content = content;
        this.create_date = create_date;
        this.edit_date = edit_date;
    }


    public Integer getId_notes() {
        return id_notes;
    }

    public void setId_notes(Integer id_notes) {
        this.id_notes = id_notes;
    }

    public Integer getId_rodent() {
        return id_rodent;
    }

    public void setId_rodent(Integer id_rodent) {
        this.id_rodent = id_rodent;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getEdit_date() {
        return edit_date;
    }

    public void setEdit_date(Date edit_date) {
        this.edit_date = edit_date;
    }
}
