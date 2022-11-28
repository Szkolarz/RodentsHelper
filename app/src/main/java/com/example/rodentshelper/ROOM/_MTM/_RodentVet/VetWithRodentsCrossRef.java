package com.example.rodentshelper.ROOM._MTM._RodentVet;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Vet.VetModel;

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

