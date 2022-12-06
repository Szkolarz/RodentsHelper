package com.example.rodentshelper.Encyclopedia.Treats;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import com.example.rodentshelper.Encyclopedia.FragmentFlag;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAORodents;
import com.example.rodentshelper.ROOM.DAOWeight;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Weights.AdapterWeights;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ViewTreats extends AppCompatActivity  {


    RecyclerView recyclerView;

    public static String[] data = {"'Zdrowe'", "Niezdrowe"};
    public static String[] getData() {
        return data;
    }

    final List<String> colors = new ArrayList<String>(){
        {
            add("#87d49c");
            add("#d48795");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.treats);

        ViewPager2 pager = findViewById(R.id.pager);



        pager.setAdapter(
                new PagerAdapterTreats(this)
        );

        TabLayout tabLayout_treats = findViewById(R.id.tabLayout_treats);
        new TabLayoutMediator(
                tabLayout_treats,
                pager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                        tab.view.setBackgroundColor(Color.parseColor(colors.get(position)));
                        tab.setText(data[position]);
                        tab.setIcon(R.drawable.ic_chinchilla);
                    }
                }
        ).attach();

        FragmentFlag.setFragmentFlag(tabLayout_treats.getSelectedTabPosition());


        tabLayout_treats.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                FragmentFlag.setFragmentFlag(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();

        }
        return super.onKeyDown(keyCode, event);
    }


}