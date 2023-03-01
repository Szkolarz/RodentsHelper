package com.gryzoniopedia.rodentshelper.ROOM.Medicaments.Methods;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAOMedicaments;
import com.gryzoniopedia.rodentshelper.ROOM.DAOVets;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentMed.MedicamentWithRodentsCrossRef;

import java.util.List;

public class MedicamentsFillList {

    public List getList (Context context, Toolbar toolbar) {

        List<MedicamentWithRodentsCrossRef> medicamentModel = null;

        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOMedicaments daoMedicaments = db.daoMedicaments();
        if (FlagSetup.getFlagMedAdd() == 2) {
            toolbar.setTitle("Lekarstwa pupila");
            SharedPreferences prefsGetRodentId = context.getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            medicamentModel = daoMedicaments.getMedsWithRodentsWhereIdRodent(prefsGetRodentId.getInt("rodentId", 0));
        }
        else {
            toolbar.setTitle("Lekarstwa");
            medicamentModel = daoMedicaments.getMedsWithRodents();
            FlagSetup.setFlagMedAdd(1);
        }
        /** !!! **/
        db.close();

        return medicamentModel;

    }
}
