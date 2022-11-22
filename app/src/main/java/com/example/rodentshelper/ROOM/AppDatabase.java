package com.example.rodentshelper.ROOM;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.rodentshelper.ROOM.Medicaments.MedicamentModel;
import com.example.rodentshelper.ROOM.Weights.WeightModel;
import com.example.rodentshelper.ROOM._MTM.RodentMedModel;
import com.example.rodentshelper.ROOM._MTM.RodentVetModel;
import com.example.rodentshelper.ROOM.Notes.NotesModel;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Vet.VetModel;
import com.example.rodentshelper.ROOM.Visits.VisitModel;

@Database(entities = {RodentModel.class,
        VetModel.class, RodentVetModel.class,
        MedicamentModel.class, RodentMedModel.class,
        VisitModel.class, NotesModel.class, WeightModel.class},
        version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DAO dao();
    public abstract DAORodents daoRodents();
    public abstract DAONotes daoNotes();
    public abstract DAOVets daoVets();
    public abstract DAOVisits daoVisits();
    public abstract DAOMedicaments daoMedicaments();
    public abstract DAOWeight daoWeight();
}

