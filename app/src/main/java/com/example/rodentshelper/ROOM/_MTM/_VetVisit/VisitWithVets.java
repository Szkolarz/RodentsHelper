package com.example.rodentshelper.ROOM._MTM._VetVisit;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.rodentshelper.ROOM.Notes.NotesModel;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Vet.VetModel;
import com.example.rodentshelper.ROOM.Visits.VisitModel;

import java.util.List;

public class VisitWithVets {

    @Embedded
    public VetModel notesModel;
    @Relation(
            parentColumn = "id_vet",//id from vet
            entityColumn = "id_vet" //id from visits
    )
    public List<VisitModel> visits;


}
