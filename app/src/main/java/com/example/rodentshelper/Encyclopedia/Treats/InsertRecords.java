package com.example.rodentshelper.Encyclopedia.Treats;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.room.Room;

import com.example.rodentshelper.Encyclopedia.CageSupply.CageSupplyModel;
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

        List<TreatsModel> treatsModel;
        List<CageSupplyModel> cageSupplyModel;

        if (FragmentFlag.getEncyclopediaTypeFlag() == 2) {
            treatsModel = daoEncyclopedia.getAllTreats3(prefsFirstStart.getInt("prefsFirstStart", 0));
            return treatsModel;
        }
        if (FragmentFlag.getEncyclopediaTypeFlag() == 3) {
            cageSupplyModel = daoEncyclopedia.getCageSupplyAdditionalInfo(prefsFirstStart.getInt("prefsFirstStart", 0));
            return cageSupplyModel;
        }

        return null;
    }

}
