package com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentWeight;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.gryzoniopedia.rodentshelper.ROOM.Rodent.RodentModel;
import com.gryzoniopedia.rodentshelper.ROOM.Weights.WeightModel;

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
