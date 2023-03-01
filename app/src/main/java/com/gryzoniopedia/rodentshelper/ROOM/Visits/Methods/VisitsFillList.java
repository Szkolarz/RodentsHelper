package com.gryzoniopedia.rodentshelper.ROOM.Visits.Methods;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAOVets;
import com.gryzoniopedia.rodentshelper.ROOM.DAOVisits;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentVisit.VisitsWithRodentsCrossRef;

import java.util.List;

public class VisitsFillList {

    public List getList (Context context, Toolbar toolbar) {

        List<VisitsWithRodentsCrossRef> visitModel;

        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOVisits daoVisits = db.daoVisits();

        if (FlagSetup.getFlagVisitAdd() == 2) {
            toolbar.setTitle("Wizyty pupila");
            SharedPreferences prefsGetRodentId = context.getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            visitModel = daoVisits.getVisitsWithRodentsWhereIdRodent(prefsGetRodentId.getInt("rodentId", 0));
        }
        else {
            toolbar.setTitle("Wizyty");
            visitModel = daoVisits.getVisitsWithRodents();
            FlagSetup.setFlagVisitAdd(1);
        }
        return visitModel;


    }
}
