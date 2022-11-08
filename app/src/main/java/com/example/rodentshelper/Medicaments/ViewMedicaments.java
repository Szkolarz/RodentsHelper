package com.example.rodentshelper.Medicaments;

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
import com.example.rodentshelper.MainViews.ViewVets;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;


import java.util.List;

public class ViewMedicaments extends AppCompatActivity {

    RecyclerView recyclerViewMedicaments;
    Button buttonAddMedicaments;

    TextView textViewEmpty_med, textView3_health;
    ImageView imageButton3_health;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medicaments);

        imageButton3_health = findViewById(R.id.imageButton3_health);
        textView3_health = findViewById(R.id.textView3_health);
        imageButton3_health.setColorFilter(Color.WHITE);
        textView3_health.setTextColor(Color.WHITE);

        buttonAddMedicaments = findViewById(R.id.buttonAddMedicaments);

        recyclerViewMedicaments = findViewById(R.id.recyclerViewMedicaments);
        recyclerViewMedicaments.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMedicaments.setHasFixedSize(true);

        getRoomData();

        textViewEmpty_med = findViewById(R.id.textViewEmpty_med);

        if (getListMedicament().isEmpty())
            textViewEmpty_med.setVisibility(View.VISIBLE);

    }

    public void addNewMedicament(View view)
    {
        //1 = nowy
        FlagSetup.setFlagMedAdd(1);
        final Context context = this;
        Intent intent = new Intent(context, AddMedicaments.class);
        startActivity(intent);
        finish();
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
        Intent intent = new Intent(ViewMedicaments.this, ViewRodents.class);
        startActivity(intent);
    }

    public void viewHealth() {
        Intent intent = new Intent(ViewMedicaments.this, ViewHealth.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewHealth();
        }
        return super.onKeyDown(keyCode, event);
    }


    public List getListMedicament(){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO medDao = db.dao();

        List<MedicamentModel> medicamentModel = medDao.getAllMedicaments();
        return medicamentModel;
    }


    public void getRoomData()
    {
        recyclerViewMedicaments = findViewById(R.id.recyclerViewMedicaments);
        recyclerViewMedicaments.setLayoutManager(new LinearLayoutManager(this));

        AdapterMedicaments adapter = new AdapterMedicaments(getListMedicament());

        recyclerViewMedicaments.setAdapter(adapter);
    }

}