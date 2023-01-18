package com.example.rodentshelper.Encyclopedia.Common;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rodentshelper.Encyclopedia.CageSupply.AdapterCageSupply;
import com.example.rodentshelper.Encyclopedia.CageSupply.CageSupplyModel;
import com.example.rodentshelper.Encyclopedia.FragmentFlag;
import com.example.rodentshelper.Encyclopedia.Treats.AdapterTreats;
import com.example.rodentshelper.Encyclopedia.Treats.TreatsModel;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAOEncyclopedia;

import java.util.List;


public class EncyclopediaTab extends Fragment {

    private final String title;


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
            cageSupplyModel = daoEncyclopedia.getAllCageSupplies(prefsFirstStart.getInt("prefsFirstStart", 0));
            return cageSupplyModel;
        }

        return null;
    }


    @Override
    public void onResume() {
        super.onResume();
        setProperHeightOfView();
    }

    private void setProperHeightOfView() {

        if (root!=null) {
            ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
            if (layoutParams!=null) {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                root.requestLayout();
            }
        }
    }

    public EncyclopediaTab(String title) {
        this.title = title;
    }

    private View root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         root = inflater.inflate(R.layout.fragment_encyclopedia_tab, container, false);

        Context context = getActivity();


        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_treats_cagesupply);
        recyclerView.setNestedScrollingEnabled(true );
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        if (FragmentFlag.getEncyclopediaTypeFlag() == 2) {
            AdapterTreats adapter = new AdapterTreats(getListOfRecords(context));
            recyclerView.setAdapter(adapter);
        }

        if (FragmentFlag.getEncyclopediaTypeFlag() == 3) {
            AdapterCageSupply adapter = new AdapterCageSupply(getListOfRecords(context));
            recyclerView.setAdapter(adapter);
        }

        return root;

    }
}