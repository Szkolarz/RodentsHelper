package com.example.rodentshelper.ROOM._MTM._RodentVisit;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Visits.VisitModel;


import java.util.List;

public class VisitsWithRodentsCrossRef {


    @Embedded
    public VisitModel visitModel;
    @Relation(
            parentColumn = "id_visit",
            entityColumn = "id_rodent",
            associateBy = @Junction(RodentVisitModel.class)
    )
    public List<RodentModel> rodents;

}

