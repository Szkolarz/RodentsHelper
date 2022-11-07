package com.example.rodentshelper.ROOM;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.rodentshelper.Medicaments.MedicamentModel;
import com.example.rodentshelper.ROOM.MTM.RodentMedModel;
import com.example.rodentshelper.ROOM.MTM.RodentVetModel;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Vet.VetModel;

@Database(entities = {RodentModel.class,
        VetModel.class, RodentVetModel.class,
        MedicamentModel.class, RodentMedModel.class},
        version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DAO dao();
}

