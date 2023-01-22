package com.android.rodentshelper.ROOM._MTM._VetVisit;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.android.rodentshelper.ROOM.Visits.VisitModel;
import com.android.rodentshelper.ROOM.Vet.VetModel;

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
