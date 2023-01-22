package com.android.rodentshelper.ROOM;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.rodentshelper.DatabaseManagement.CloudAccountModel;
import com.android.rodentshelper.DatabaseManagement.DatabaseManagementModel;
import com.android.rodentshelper.Encyclopedia.CageSupply.CageSupplyModel;
import com.android.rodentshelper.Encyclopedia.Diseases.DiseasesModel;
import com.android.rodentshelper.Encyclopedia.General.GeneralModel;
import com.android.rodentshelper.Encyclopedia.Treats.TreatsModel;
import com.android.rodentshelper.Encyclopedia.Version.VersionModel;
import com.android.rodentshelper.Notifications.NotificationsModel;
import com.android.rodentshelper.ROOM.Medicaments.MedicamentModel;
import com.android.rodentshelper.ROOM.Notes.NotesModel;
import com.android.rodentshelper.ROOM.Rodent.RodentModel;
import com.android.rodentshelper.ROOM.Vet.VetModel;
import com.android.rodentshelper.ROOM.Visits.VisitModel;
import com.android.rodentshelper.ROOM.Weights.WeightModel;
import com.android.rodentshelper.ROOM._MTM._RodentMed.RodentMedModel;
import com.android.rodentshelper.ROOM._MTM._RodentVet.RodentVetModel;
import com.android.rodentshelper.ROOM._MTM._RodentVisit.RodentVisitModel;

@Database(entities = { RodentModel.class,
        VetModel.class, RodentVetModel.class,
        MedicamentModel.class, RodentMedModel.class,
        VisitModel.class, NotesModel.class, WeightModel.class,
        RodentVisitModel.class, VersionModel.class,
        NotificationsModel.class,
        TreatsModel.class, CageSupplyModel.class,
        GeneralModel.class, DiseasesModel.class, DatabaseManagementModel.class,
        CloudAccountModel.class},
        version = 1)
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

