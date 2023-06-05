package com.gryzoniopedia.rodentshelper.ROOM.Rodent.Methods;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAONotes;
import com.gryzoniopedia.rodentshelper.ROOM.DAORodents;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.RodentModel;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.ViewRodents;

import java.util.List;

public class RodentsFillList {

    public List getList (Context context) {


        SharedPreferences prefsFirstStart = context.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAORodents daoRodents = db.daoRodents();

        SharedPreferences spRodentsRadioOrder = context.getSharedPreferences("spRodentsRadioOrder", MODE_PRIVATE);

        if (spRodentsRadioOrder.getInt("spRodentsRadioOrder", 1) == 1)
            return daoRodents.getAllRodentsWhereIdAnimalASC(prefsFirstStart.getInt("prefsFirstStart", 1));
        if (spRodentsRadioOrder.getInt("spRodentsRadioOrder", 1) == 2)
            return daoRodents.getAllRodentsWhereIdAnimalDESC(prefsFirstStart.getInt("prefsFirstStart", 1));
        if (spRodentsRadioOrder.getInt("spRodentsRadioOrder", 1) == 3)
            return daoRodents.getAllRodentsWhereIdAnimalOrderByNameASC(prefsFirstStart.getInt("prefsFirstStart", 1));
        if (spRodentsRadioOrder.getInt("spRodentsRadioOrder", 1) == 4)
            return daoRodents.getAllRodentsWhereIdAnimalOrderByNameDESC(prefsFirstStart.getInt("prefsFirstStart", 1));


        db.close();

        return daoRodents.getAllRodentsWhereIdAnimalASC(prefsFirstStart.getInt("prefsFirstStart", 1));

    }
}
