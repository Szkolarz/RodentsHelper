package com.example.rodentshelper.Visits;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.MainViews.ViewHealth;
import com.example.rodentshelper.MainViews.ViewRodents;
import com.example.rodentshelper.Medicaments.AdapterMedicaments;
import com.example.rodentshelper.Medicaments.AddMedicaments;
import com.example.rodentshelper.Medicaments.MedicamentModel;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;

import java.util.List;

public class ViewVisits extends AppCompatActivity {

    RecyclerView recyclerViewVisits;
    Button buttonAddVisits;

    TextView textViewEmpty_visit, textView3_health;
    ImageView imageButton3_health;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_visits);

        imageButton3_health = findViewById(R.id.imageButton3_health);
        textView3_health = findViewById(R.id.textView3_health);
        imageButton3_health.setColorFilter(Color.WHITE);
        textView3_health.setTextColor(Color.WHITE);

        buttonAddVisits = findViewById(R.id.buttonAddVisits);

        recyclerViewVisits = findViewById(R.id.recyclerViewVisits);
        recyclerViewVisits.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewVisits.setHasFixedSize(true);

        getRoomData();

        textViewEmpty_visit = findViewById(R.id.textViewEmpty_visit);

        if (getListVisits().isEmpty())
            textViewEmpty_visit.setVisibility(View.VISIBLE);

    }

    public void addNewVisit(View view)
    {
        //1 = nowy
        FlagSetup.setFlagVisitAdd(1);
        final Context context = this;
        Intent intent = new Intent(context, AddVisits.class);
        startActivity(intent);
    }



    public void onClickNavHealth(View view)
    {
        viewHealth();
    }

    public void onClickNavRodent(View view)
    {
        viewRodents();
    }

    public void viewRodents() {
        Intent intent = new Intent(ViewVisits.this, ViewRodents.class);
        startActivity(intent);
    }

    public void viewHealth() {
        Intent intent = new Intent(ViewVisits.this, ViewHealth.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewHealth();
        }
        return super.onKeyDown(keyCode, event);
    }


    public List getListVisits(){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO visitDao = db.dao();

        List<VisitModel> visitModel = visitDao.getAllVisits();
        return visitModel;
    }


    public void getRoomData()
    {
        recyclerViewVisits = findViewById(R.id.recyclerViewVisits);
        recyclerViewVisits.setLayoutManager(new LinearLayoutManager(this));

        AdapterVisits adapter = new AdapterVisits(getListVisits());

        recyclerViewVisits.setAdapter(adapter);
    }

}