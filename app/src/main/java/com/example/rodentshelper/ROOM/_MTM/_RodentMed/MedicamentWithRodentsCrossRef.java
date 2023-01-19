package com.example.rodentshelper.ROOM._MTM._RodentMed;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.rodentshelper.ROOM.Medicaments.MedicamentModel;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;

import java.util.List;

public class MedicamentWithRodentsCrossRef {

    @Embedded
    public MedicamentModel medicamentModel;
    @Relation(
            parentColumn = "id_medicament",
            entityColumn = "id_rodent",
            associateBy = @Junction(RodentMedModel.class)
    )
    public List<RodentModel> rodents;

}

