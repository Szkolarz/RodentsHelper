package com.gryzoniopedia.rodentshelper.ROOM;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.gryzoniopedia.rodentshelper.DatabaseManagement.CloudAccountModel;
import com.gryzoniopedia.rodentshelper.DatabaseManagement.DatabaseManagementModel;
import com.gryzoniopedia.rodentshelper.Encyclopedia.CageSupply.CageSupplyModel;
import com.gryzoniopedia.rodentshelper.Encyclopedia.Diseases.DiseasesModel;
import com.gryzoniopedia.rodentshelper.Encyclopedia.General.GeneralModel;
import com.gryzoniopedia.rodentshelper.Encyclopedia.Treats.TreatsModel;
import com.gryzoniopedia.rodentshelper.Encyclopedia.Version.VersionModel;
import com.gryzoniopedia.rodentshelper.Notifications.NotificationsModel;
import com.gryzoniopedia.rodentshelper.ROOM.Medicaments.MedicamentModel;
import com.gryzoniopedia.rodentshelper.ROOM.Notes.NotesModel;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.RodentModel;
import com.gryzoniopedia.rodentshelper.ROOM.Vet.VetModel;
import com.gryzoniopedia.rodentshelper.ROOM.Visits.VisitModel;
import com.gryzoniopedia.rodentshelper.ROOM.Weights.WeightModel;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentMed.RodentMedModel;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentVet.RodentVetModel;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentVisit.RodentVisitModel;

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

