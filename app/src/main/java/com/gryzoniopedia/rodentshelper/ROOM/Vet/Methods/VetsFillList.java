package com.gryzoniopedia.rodentshelper.ROOM.Vet.Methods;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAORodents;
import com.gryzoniopedia.rodentshelper.ROOM.DAOVets;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentVet.VetWithRodentsCrossRef;

import java.util.List;

public class VetsFillList {

    public List getList (Context context, Toolbar toolbar) {

        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOVets daoVets = db.daoVets();
        if (FlagSetup.getFlagVetAdd() == 2) {
            toolbar.setTitle("Weterynarze pupila");
            SharedPreferences prefsGetRodentId = context.getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            db.close();
            return daoVets.getVetsWithRodentsWhereIdRodent(prefsGetRodentId.getInt("rodentId", 0));
        }
        else {
            toolbar.setTitle("Weterynarze");
            db.close();
            FlagSetup.setFlagVetAdd(1);
            return daoVets.getVetsWithRodents();
        }


    }
}
