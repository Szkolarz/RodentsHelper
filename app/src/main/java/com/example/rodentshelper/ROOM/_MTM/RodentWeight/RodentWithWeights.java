package com.example.rodentshelper.ROOM._MTM.RodentWeight;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.rodentshelper.ROOM.Notes.NotesModel;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Weights.WeightModel;

import java.util.List;

public class RodentWithWeights {

    @Embedded
    public WeightModel weightModel;
    @Relation(
            parentColumn = "id_rodent",//id from weights
            entityColumn = "id_rodent" //id from rodents
    )
    public List<RodentModel> rodents;


}
