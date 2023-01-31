package com.gryzoniopedia.rodentshelper.ROOM.Visits;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAOVisits;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentVisit.VisitsWithRodentsCrossRef;

import java.util.List;
import java.util.Objects;

public class ViewVisits extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recycler);

        toolbar = findViewById(R.id.toolbar_main);

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
            if (!FlagSetup.getFlagIsFromHealth()) {
                imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
                TextView textView1_rodent = findViewById(R.id.textView1_rodent);
                imageButton1_rodent.setColorFilter(Color.WHITE);
                textView1_rodent.setTextColor(Color.WHITE);
            } else if (FlagSetup.getFlagIsFromHealth() || prefsNotificationVisit.getBoolean("prefsNotificationVisit", false)) {
                imageButton3_health = findViewById(R.id.imageButton3_health);
                TextView textView3_health = findViewById(R.id.textView3_health);
                imageButton3_health.setColorFilter(Color.WHITE);
                textView3_health.setTextColor(Color.WHITE);
            }
        } catch (NullPointerException e) {
            FlagSetup.setFlagIsFromHealth(true);
            FlagSetup.setFlagVisitAdd(0);
        }


        Button buttonAddRecord = findViewById(R.id.buttonAddRecord);

        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getRoomData();

        TextView textViewEmpty_visit = findViewById(R.id.textViewEmptyGlobal);

        if (getListVisits().isEmpty()) {
            textViewEmpty_visit.setVisibility(View.VISIBLE);
            textViewEmpty_visit.setText("Nie ma żadnych pozycji w bazie danych. Aby dodać nową wizytę, kliknij przycisk z plusikiem na górze ekranu.");
        }

        buttonAddRecord.setOnClickListener(view -> addNewVisit());

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

    }

    public void addNewVisit()
    {

        if (FlagSetup.getFlagIsFromHealth())
            FlagSetup.setFlagVisitAdd(1);
        else
            FlagSetup.setFlagVisitAdd(2);
        //1 = nowy
        //FlagSetup.setFlagVisitAdd(1);
        final Context context = this;
        Intent intent = new Intent(context, AddEditVisits.class);
        startActivity(intent);
        finish();
    }




    public List getListVisits(){
        List<VisitsWithRodentsCrossRef> visitModel;

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOVisits daoVisits = db.daoVisits();

        if (FlagSetup.getFlagVisitAdd() == 2) {
            toolbar.setTitle("Wizyty pupila");
            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            visitModel = daoVisits.getVisitsWithRodentsWhereIdRodent(prefsGetRodentId.getInt("rodentId", 0));
        }
        else {
            toolbar.setTitle("Wizyty");
            visitModel = daoVisits.getVisitsWithRodents();
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