package com.example.rodentshelper.Encyclopedia.Common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.rodentshelper.Encyclopedia.CageSupply.CageSupplyModel;
import com.example.rodentshelper.Encyclopedia.FragmentFlag;
import com.example.rodentshelper.Encyclopedia.Treats.InsertRecords;
import com.example.rodentshelper.Encyclopedia.Treats.PagerAdapterTreats;
import com.example.rodentshelper.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class ViewEncyclopediaData extends AppCompatActivity  {


    RecyclerView recyclerView;

    public static String[] data;

    private void getProperDataValues() {

        if (FragmentFlag.getEncyclopediaTypeFlag() == 2)
            data = new String[]{"Zdrowe", "Niezdrowe"};
        if (FragmentFlag.getEncyclopediaTypeFlag() == 3)
            data = new String[]{"Potrzebne", "Nieodpowiednie"};
    }


    public static String[] getData() {
        return data;
    }


    int[] myImageList = new int[]{
          R.drawable.check,
          R.drawable.cancel
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.treats);

        getProperDataValues();

        ViewPager2 pager = findViewById(R.id.pager);
        pager.setUserInputEnabled(false);

        ImageView imageView_encyclopedia = findViewById(R.id.imageView_encyclopedia);
        TextView textViewInfo_encyclopedia = findViewById(R.id.textViewInfo_encyclopedia);
        LinearLayout linearLayout_encyclopedia = findViewById(R.id.linearLayout_encyclopedia);

        InsertRecords insertRecords = new InsertRecords();
        if (FragmentFlag.getEncyclopediaTypeFlag() == 3) {
            List<CageSupplyModel> cageSupplyModel = insertRecords.getListOfRecords(getApplicationContext());
            textViewInfo_encyclopedia.setText(cageSupplyModel.get(0).getDescription());

            linearLayout_encyclopedia.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeByteArray(cageSupplyModel.get(0).getImage(),
                    0, cageSupplyModel.get(0).getImage().length);
            imageView_encyclopedia.setImageBitmap(bitmap);
        }


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

                      //  tab.view.setBackgroundColor(Color.parseColor(colors.get(position)));
                        tab.setText(data[position]);

                        tab.setIcon(myImageList[position]);
                        //tab.setIcon(R.id.)

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