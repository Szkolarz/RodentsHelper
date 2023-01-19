package com.example.rodentshelper.Encyclopedia.Common;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.room.Room;
import com.example.rodentshelper.Encyclopedia.FragmentFlag;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAOEncyclopedia;
import java.util.List;


public class InsertRecords {

    public List getListOfRecords(Context context){
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOEncyclopedia daoEncyclopedia = db.daoEncyclopedia();

        SharedPreferences prefsFirstStart = context.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);
        Integer prefsRodentId = prefsFirstStart.getInt("prefsFirstStart", 0);


        if (FragmentFlag.getEncyclopediaTypeFlag() == 1) {
            return daoEncyclopedia.getGeneralAdditionalInfo(prefsRodentId);
        }
        if (FragmentFlag.getEncyclopediaTypeFlag() == 2) {
            return daoEncyclopedia.getAllTreats3(prefsRodentId);
        }
        if (FragmentFlag.getEncyclopediaTypeFlag() == 3) {
            return daoEncyclopedia.getCageSupplyAdditionalInfo(prefsRodentId);
        }
        if (FragmentFlag.getEncyclopediaTypeFlag() == 4) {
            return daoEncyclopedia.getDiseasesAdditionalInfo(prefsRodentId);
        }

        db.close();

        return null;
    }

}
