package com.android.rodentshelper.Encyclopedia.Common;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.android.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.android.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.android.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.android.rodentshelper.Encyclopedia.FragmentFlag;
import com.android.rodentshelper.Encyclopedia.Diseases.AdapterDiseases;
import com.android.rodentshelper.Encyclopedia.Diseases.DiseasesModel;
import com.android.rodentshelper.Encyclopedia.General.AdapterGeneral;
import com.android.rodentshelper.Encyclopedia.General.GeneralModel;
import com.example.rodentshelper.R;
import com.android.rodentshelper.ROOM.AppDatabase;
import com.android.rodentshelper.ROOM.DAOEncyclopedia;

import java.util.List;
import java.util.Objects;

public class ViewGeneralAndDiseases extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_with_photo);

        Toolbar toolbar = findViewById(R.id.toolbar_main);

        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;
        TextView textView2_encyclopedia;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);

        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());


        textView2_encyclopedia = findViewById(R.id.textView2_encyclopedia);
        imageButton2_encyclopedia.setColorFilter(Color.WHITE);
        textView2_encyclopedia.setTextColor(Color.WHITE);


        ImageView imageView_general = findViewById(R.id.imageView_general);
        TextView textViewName_general = findViewById(R.id.textViewName_general);
        TextView textViewDesc_general = findViewById(R.id.textViewDesc_general);
        CardView cardView_treats = findViewById(R.id.cardView_treats);

        LinearLayout linearLayout_general = findViewById(R.id.linearLayout_general);

        View view_general = findViewById(R.id.view_general);


        InsertRecords insertRecords = new InsertRecords();

        SharedPreferences prefsFirstStart = ViewGeneralAndDiseases.this.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);
        int prefsRodentId = prefsFirstStart.getInt("prefsFirstStart", 0);

        if (FragmentFlag.getEncyclopediaTypeFlag() == 1) {
            toolbar.setTitle("Ogólne informacje");
            List<GeneralModel> generalModel = insertRecords.getListOfRecords(getApplicationContext());
            cardView_treats.setVisibility(View.VISIBLE);

            Bitmap bitmap = BitmapFactory.decodeByteArray(generalModel.get(0).getImage(),
                    0, generalModel.get(0).getImage().length);
            imageView_general.setImageBitmap(bitmap);

            if (prefsRodentId == 3)
                textViewName_general.setText("SZYNSZYLA");
            else if (prefsRodentId == 2)
                textViewName_general.setText("SZCZUR");
            else if (prefsRodentId == 1)
                textViewName_general.setText("ŚWINKA MORSKA");

            textViewDesc_general.setText(generalModel.get(0).getDescription());
            getRoomData(prefsRodentId);
        } else {
            toolbar.setTitle("Spis chorób");
            List<DiseasesModel> diseasesModel = insertRecords.getListOfRecords(getApplicationContext());
            textViewName_general.setText("Choroby");
            linearLayout_general.setBackgroundColor(Color.parseColor("#f6fad4"));
            view_general.setBackgroundColor(Color.parseColor("#f3ff8c"));

            textViewDesc_general.setText(diseasesModel.get(0).getDescription());
            getRoomData(prefsRodentId);
        }

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }


    public List getListGeneral(Integer prefsRodentId){

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOEncyclopedia daoEncyclopedia = db.daoEncyclopedia();

        List<GeneralModel> generalModel = daoEncyclopedia.getAllGeneral(prefsRodentId);

        db.close();
        return generalModel;
    }

    private List getListDiseases(Integer prefsRodentId){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOEncyclopedia daoEncyclopedia = db.daoEncyclopedia();

        List<DiseasesModel> diseasesModel = daoEncyclopedia.getAllDiseases(prefsRodentId);

        db.close();
        return diseasesModel;
    }


    private void getRoomData(Integer prefsRodentId) {

        RecyclerView recyclerView = findViewById(R.id.recyclerView_general);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewGeneralAndDiseases.this));
        recyclerView.setNestedScrollingEnabled(false);

        if (FragmentFlag.getEncyclopediaTypeFlag() == 1) {
            AdapterGeneral adapter = new AdapterGeneral(getListGeneral(prefsRodentId));
            recyclerView.setAdapter(adapter);
        } else {
            AdapterDiseases adapter = new AdapterDiseases(getListDiseases(prefsRodentId));
            recyclerView.setAdapter(adapter);
        }

    }




}