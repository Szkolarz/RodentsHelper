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

        db.close();

        return daoRodents.getAllRodentsWhereIdAnimal(prefsFirstStart.getInt("prefsFirstStart", 0));

    }
}
