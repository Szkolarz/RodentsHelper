package com.example.rodentshelper.ROOM;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.rodentshelper.Encyclopedia.CageSupply.CageSupplyModel;
import com.example.rodentshelper.Encyclopedia.General.GeneralModel;
import com.example.rodentshelper.Encyclopedia.Treats.TreatsModel;
import com.example.rodentshelper.Encyclopedia.Version.VersionModel;
import com.example.rodentshelper.Notifications.NotificationsModel;
import com.example.rodentshelper.ROOM.Medicaments.MedicamentModel;
import com.example.rodentshelper.ROOM.Weights.WeightModel;
import com.example.rodentshelper.ROOM._MTM._RodentMed.RodentMedModel;
import com.example.rodentshelper.ROOM._MTM._RodentVet.RodentVetModel;
import com.example.rodentshelper.ROOM.Notes.NotesModel;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Vet.VetModel;
import com.example.rodentshelper.ROOM.Visits.VisitModel;
import com.example.rodentshelper.ROOM._MTM._RodentVisit.RodentVisitModel;

@Database(entities = { RodentModel.class,
        VetModel.class, RodentVetModel.class,
        MedicamentModel.class, RodentMedModel.class,
        VisitModel.class, NotesModel.class, WeightModel.class,
        RodentVisitModel.class, VersionModel.class,
        NotificationsModel.class,
        TreatsModel.class, CageSupplyModel.class,
        GeneralModel.class},
        version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DAO dao();
    public abstract DAORodents daoRodents();
    public abstract DAONotes daoNotes();
    public abstract DAOVets daoVets();
    public abstract DAOVisits daoVisits();
    public abstract DAOMedicaments daoMedicaments();
    public abstract DAOWeight daoWeight();
    public abstract DAOEncyclopedia daoEncyclopedia();
    public abstract DAONotifications daoNotifications();
}

