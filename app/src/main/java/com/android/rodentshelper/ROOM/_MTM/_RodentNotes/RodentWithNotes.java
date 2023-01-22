package com.android.rodentshelper.ROOM._MTM._RodentNotes;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.android.rodentshelper.ROOM.Notes.NotesModel;
import com.android.rodentshelper.ROOM.Rodent.RodentModel;

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
