package com.example.rodentshelper.ROOM._MTM;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.rodentshelper.ROOM.Notes.NotesModel;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;

import java.util.List;

public class RodentWithNotes {

    @Embedded
    public NotesModel notesModel;
    @Relation(
            parentColumn = "id_rodent",//id from notes
            entityColumn = "id_rodent" //id from rodents
    )
    public List<RodentModel> rodents;


}
