package com.gryzoniopedia.rodentshelper.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.gryzoniopedia.rodentshelper.ROOM.Notes.NotesModel;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentNotes.RodentWithNotes;

import java.util.List;

@Dao
public interface DAONotes {

    @Query("SELECT * FROM notes WHERE id_rodent = :id_rodent ORDER BY id_notes ASC")
    List<NotesModel> getAllNotes(Integer id_rodent);

    @Query("DELETE FROM notes WHERE id_notes = :id")
    void deleteNotesById(Integer id);

    @Query("UPDATE notes SET topic = :topic, content = :content, image1 = :image1,  image2 = :image2," +
            "image3 = :image3, image4 = :image4  WHERE id_notes = :id")
    void updateNotesById(Integer id, String topic, String content, byte[] image1, byte[] image2, byte[] image3, byte[] image4);



    @Query ("SELECT rodents.id_rodent FROM rodents WHERE rodents.id_rodent = :id")
    Integer getIdofRodent(Integer id);



    @Insert
    void insertRecordNotes(NotesModel notes);



    @Transaction
    @Query("SELECT * FROM notes WHERE id_rodent = :id_rodent ORDER BY id_notes DESC")
    List<RodentWithNotes> getRodentWithNotesDESC(Integer id_rodent);

    @Transaction
    @Query("SELECT * FROM notes WHERE id_rodent = :id_rodent ORDER BY id_notes ASC")
    List<RodentWithNotes> getRodentWithNotesASC(Integer id_rodent);



    @Transaction
    @Query("SELECT * FROM notes WHERE create_date >= ((SELECT STRFTIME('%s') * 1000) - 2592000000) AND " +
            "id_rodent = :id_rodent ORDER BY id_notes ASC")
    List<RodentWithNotes> getRodentWithNotes1MonthASC(Integer id_rodent);
    @Transaction
    @Query("SELECT * FROM notes WHERE create_date >= ((SELECT STRFTIME('%s') * 1000) - 2592000000) AND " +
            "id_rodent = :id_rodent ORDER BY id_notes DESC")
    List<RodentWithNotes> getRodentWithNotes1MonthDESC(Integer id_rodent);

    @Transaction
    @Query("SELECT * FROM notes WHERE create_date >= ((SELECT STRFTIME('%s') * 1000) - 7689600000) AND " +
            "id_rodent = :id_rodent ORDER BY id_notes ASC")
    List<RodentWithNotes> getRodentWithNotes3MonthsASC(Integer id_rodent);
    @Transaction
    @Query("SELECT * FROM notes WHERE create_date >= ((SELECT STRFTIME('%s') * 1000) - 7689600000) AND " +
            "id_rodent = :id_rodent ORDER BY id_notes DESC")
    List<RodentWithNotes> getRodentWithNotes3MonthsDESC(Integer id_rodent);

    @Transaction
    @Query("SELECT * FROM notes WHERE create_date >= ((SELECT STRFTIME('%s') * 1000) - 15638400000) AND " +
            "id_rodent = :id_rodent ORDER BY id_notes ASC")
    List<RodentWithNotes> getRodentWithNotes6MonthsASC(Integer id_rodent);
    @Transaction
    @Query("SELECT * FROM notes WHERE create_date >= ((SELECT STRFTIME('%s') * 1000) - 15638400000) AND " +
            "id_rodent = :id_rodent ORDER BY id_notes DESC")
    List<RodentWithNotes> getRodentWithNotes6MonthsDESC(Integer id_rodent);

    @Transaction
    @Query("SELECT * FROM notes WHERE create_date >= ((SELECT STRFTIME('%s') * 1000) - 31536000000) AND " +
            "id_rodent = :id_rodent ORDER BY id_notes ASC")
    List<RodentWithNotes> getRodentWithNotes1YearASC(Integer id_rodent);
    @Transaction
    @Query("SELECT * FROM notes WHERE create_date >= ((SELECT STRFTIME('%s') * 1000) - 31536000000) AND " +
            "id_rodent = :id_rodent ORDER BY id_notes DESC")
    List<RodentWithNotes> getRodentWithNotes1YearDESC(Integer id_rodent);


    @Query("SELECT image1 FROM notes WHERE id_notes = :id_notes")
    byte[] getNotesImage1ById(Integer id_notes);

    @Query("SELECT image2 FROM notes WHERE id_notes = :id_notes")
    byte[] getNotesImage2ById(Integer id_notes);

    @Query("SELECT image3 FROM notes WHERE id_notes = :id_notes")
    byte[] getNotesImage3ById(Integer id_notes);

    @Query("SELECT image4 FROM notes WHERE id_notes = :id_notes")
    byte[] getNotesImage4ById(Integer id_notes);
}


