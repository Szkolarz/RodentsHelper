package com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentVet;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.gryzoniopedia.rodentshelper.ROOM.Rodent.RodentModel;
import com.gryzoniopedia.rodentshelper.ROOM.Vet.VetModel;

import java.util.List;

public class VetWithRodentsCrossRef {


    @Embedded
    public VetModel vetModel;
    @Relation(
            parentColumn = "id_vet",
            entityColumn = "id_rodent",
            associateBy = @Junction(RodentVetModel.class)
    )
    public List<RodentModel> rodents;

}

