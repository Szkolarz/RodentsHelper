package com.example.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.rodentshelper.ROOM.Notes.NotesModel;

import java.util.List;

@Dao
public interface DAONotes {

    @Query("SELECT * FROM notes WHERE id_rodent = :id_rodent ORDER BY id ASC")
    List<NotesModel> getAllNotes(Integer id_rodent);

    @Query("DELETE FROM notes WHERE id = :id")
    void deleteNotesById(Integer id);

    @Query("UPDATE notes SET topic = :topic, content = :content WHERE id = :id")
    void updatNotesById(Integer id, String topic, String content);

    @Query("SELECT vets.name FROM vets WHERE vets.id = :id")
    List<String> getAllVisitsVets(Integer id);

    @Query ("SELECT rodents.id FROM rodents WHERE rodents.id = :id")
    Integer getIdofRodent(Integer id);



    @Insert
    void insertRecordNotes(NotesModel notes);

}


