package com.example.rodentshelper.ROOM.Visits;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.MainViews.ViewEncyclopedia;
import com.example.rodentshelper.MainViews.ViewHealth;
import com.example.rodentshelper.MainViews.ViewOther;
import com.example.rodentshelper.ROOM.DAOVets;
import com.example.rodentshelper.ROOM.DAOVisits;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.Vet.ViewVets;
import com.example.rodentshelper.ROOM._MTM._RodentVet.VetWithRodentsCrossRef;
import com.example.rodentshelper.ROOM._MTM._RodentVisit.VisitsWithRodentsCrossRef;

import java.util.List;

public class ViewVisits extends AppCompatActivity {

    RecyclerView recyclerView;
    Button buttonAddRecord;

    TextView textViewEmpty_visit, textView3_health, textView1_rodent;

    private AppDatabase getAppDatabase () {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        return db;
    }

    private DAOVisits getDaoVisits () {
        DAOVisits daoVisits = getAppDatabase().daoVisits();
        return daoVisits;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recycler);

        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);

        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());

        SharedPreferences prefsNotificationVisit = ViewVisits.this.getSharedPreferences("prefsNotificationVisit", Context.MODE_PRIVATE);


        try {
            if (FlagSetup.getFlagIsFromHealth() == false) {
                imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
                textView1_rodent = findViewById(R.id.textView1_rodent);
                imageButton1_rodent.setColorFilter(Color.WHITE);
                textView1_rodent.setTextColor(Color.WHITE);
            } else if (FlagSetup.getFlagIsFromHealth() == true || prefsNotificationVisit.getBoolean("prefsNotificationVisit", false)) {
                imageButton3_health = findViewById(R.id.imageButton3_health);
                textView3_health = findViewById(R.id.textView3_health);
                imageButton3_health.setColorFilter(Color.WHITE);
                textView3_health.setTextColor(Color.WHITE);
            }
        } catch (NullPointerException e) {
            FlagSetup.setFlagIsFromHealth(true);
            FlagSetup.setFlagVisitAdd(0);
        }


        buttonAddRecord = findViewById(R.id.buttonAddRecord);

        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getRoomData();

        textViewEmpty_visit = findViewById(R.id.textViewEmptyGlobal);

        if (getListVisits().isEmpty()) {
            textViewEmpty_visit.setVisibility(View.VISIBLE);
            textViewEmpty_visit.setText("Nie ma żadnych pozycji w bazie danych. Aby dodać nową wizytę, kliknij przycisk z plusikiem na górze ekranu.");
        }

        buttonAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewVisit();
            }
        });

    }

    public void addNewVisit()
    {

        if (FlagSetup.getFlagIsFromHealth() == true)
            FlagSetup.setFlagVisitAdd(1);
        else
            FlagSetup.setFlagVisitAdd(2);
        //1 = nowy
        //FlagSetup.setFlagVisitAdd(1);
        final Context context = this;
        Intent intent = new Intent(context, AddVisits.class);
        startActivity(intent);
    }






    public List getListVisits(){


        List<VisitsWithRodentsCrossRef> visitModel = null;

        if (FlagSetup.getFlagVisitAdd() == 2) {
            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            visitModel = getDaoVisits().getVisitsWithRodentsWhereIdRodent(prefsGetRodentId.getInt("rodentId", 0));
        }
        else {
            visitModel = getDaoVisits().getVisitsWithRodents();
            FlagSetup.setFlagVisitAdd(1);
        }

        return visitModel;
    }


    public void getRoomData()
    {
        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterVisits adapter = new AdapterVisits(getListVisits());

        recyclerView.setAdapter(adapter);
    }



}