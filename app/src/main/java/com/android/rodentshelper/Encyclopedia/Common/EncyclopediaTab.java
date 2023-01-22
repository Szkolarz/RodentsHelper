package com.android.rodentshelper.Encyclopedia.Common;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.rodentshelper.Encyclopedia.CageSupply.AdapterCageSupply;
import com.android.rodentshelper.Encyclopedia.CageSupply.CageSupplyModel;
import com.android.rodentshelper.Encyclopedia.Treats.AdapterTreats;
import com.android.rodentshelper.Encyclopedia.Treats.TreatsModel;
import com.android.rodentshelper.Encyclopedia.FragmentFlag;
import com.example.rodentshelper.R;
import com.android.rodentshelper.ROOM.AppDatabase;
import com.android.rodentshelper.ROOM.DAOEncyclopedia;

import java.util.List;


public class EncyclopediaTab extends Fragment {

    public List getListOfRecords(Context context){
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOEncyclopedia daoEncyclopedia = db.daoEncyclopedia();

        SharedPreferences prefsFirstStart = context.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        List<TreatsModel> treatsModel;
        List<CageSupplyModel> cageSupplyModel;

        if (FragmentFlag.getEncyclopediaTypeFlag() == 2) {
            if (FragmentFlag.getFragmentFlag() == 0) {
                treatsModel = daoEncyclopedia.getAllTreatsHealthy(prefsFirstStart.getInt("prefsFirstStart", 0));
                return treatsModel;
            } else {
                treatsModel = daoEncyclopedia.getAllTreatsNotHealthy(prefsFirstStart.getInt("prefsFirstStart", 0));
                return treatsModel;
            }

        }
        if (FragmentFlag.getEncyclopediaTypeFlag() == 3) {

            if (FragmentFlag.getFragmentFlag() == 0) {
                cageSupplyModel = daoEncyclopedia.getAllCageSuppliesGood(prefsFirstStart.getInt("prefsFirstStart", 0));
                return cageSupplyModel;
            } else {
                cageSupplyModel = daoEncyclopedia.getAllCageSuppliesNotGood(prefsFirstStart.getInt("prefsFirstStart", 0));
                return cageSupplyModel;
            }
        }

        return null;
    }


    @Override
    public void onResume() {
        super.onResume();
        setProperHeightOfView();
    }


    //without this method the recyclerview in tablayout is messing up
    //and leaves some empty-scrollable space
    private void setProperHeightOfView() {

        //if Fragment = Treats
        if (FragmentFlag.getEncyclopediaTypeFlag() == 2) {
            if (root != null) {
                ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
                if (layoutParams != null) {
                    adapterTreats = new AdapterTreats(getListOfRecords(context));
                    recyclerView.setAdapter(adapterTreats);
                }
            }
        }

        //if Fragment = CageSupply
        if (FragmentFlag.getEncyclopediaTypeFlag() == 3) {
            if (root != null) {
                ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
                if (layoutParams != null) {
                    adapterCageSupply = new AdapterCageSupply(getListOfRecords(context));
                    recyclerView.setAdapter(adapterCageSupply);
                }
            }
        }
    }



    private AdapterTreats adapterTreats;
    private AdapterCageSupply adapterCageSupply;
    private Context context;
    private RecyclerView recyclerView;
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_encyclopedia_tab, container, false);
        context = getActivity();

        recyclerView = root.findViewById(R.id.recyclerView_treats_cagesupply);
        recyclerView.setNestedScrollingEnabled(true );
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        if (FragmentFlag.getEncyclopediaTypeFlag() == 2) {
            adapterTreats = new AdapterTreats(getListOfRecords(context));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapterTreats);
        }

        if (FragmentFlag.getEncyclopediaTypeFlag() == 3) {
            adapterCageSupply = new AdapterCageSupply(getListOfRecords(context));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapterCageSupply);
        }

        return root;

    }
}