package com.example.rodentshelper.ROOM.Rodent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.MainViews.ViewEncyclopedia;
import com.example.rodentshelper.MainViews.ViewHealth;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.MainViews.ViewOther;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.DAORodents;

import java.util.List;

public class ViewRodents extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textViewEmpty_rodent, textView1_rodent;
    ImageView imageButton1_rodent;
    Button buttonAddRecord;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recycler);

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        textView1_rodent = findViewById(R.id.textView1_rodent);
        imageButton1_rodent.setColorFilter(Color.WHITE);
        textView1_rodent.setTextColor(Color.WHITE);

        buttonAddRecord = findViewById(R.id.buttonAddRecord);

        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getRoomData(ViewRodents.this);


        textViewEmpty_rodent = findViewById(R.id.textViewEmptyGlobal);

        if (getListRodent(ViewRodents.this).isEmpty()) {
            textViewEmpty_rodent.setVisibility(View.VISIBLE);
            textViewEmpty_rodent.setText("Nie ma żadnych pozycji w bazie danych. Aby dodać nowego pupila, kliknij przycisk z plusikiem na górze ekranu.");
        }

            buttonAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewRodent();
            }
        });


      /*  if (rodentsModelClassList.size() > 0) {
            RodentsAdapterClass rodentsAdapterClass = new RodentsAdapterClass(rodentsModelClassList, ViewRodents.this);
            recyclerViewRodents.setAdapter(rodentsAdapterClass);
        }
        else {
            Toast.makeText(this, "Nie ma nic w bazie", Toast.LENGTH_SHORT).show();
            System.out.println("NIE MA NIC W BAZIE");
        }*/

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Naciśnij jeszcze raz, aby zamknąć aplikację", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    public void addNewRodent()
    {
        //1 = nowy
        FlagSetup.setFlagRodentAdd(1);
        finish();
        Intent intent = new Intent(ViewRodents.this, AddRodents.class);
        startActivity(intent);
    }

    public List getListRodent(ViewRodents viewRodents){
        SharedPreferences prefsFirstStart = viewRodents.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAORodents daoRodents = db.daoRodents();

        List<RodentModel> rodentModel = daoRodents.getAllRodents(prefsFirstStart.getInt("prefsFirstStart", 0));

        return rodentModel;
    }


    public void getRoomData(ViewRodents viewRodents)
    {
        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterRodents adapter = new AdapterRodents(getListRodent(viewRodents));

        recyclerView.setAdapter(adapter);
    }




    public void onClickNavRodent(View view) {

    }

    public void onClickNavEncyclopedia(View view) {
        Intent intent = new Intent(ViewRodents.this, ViewEncyclopedia.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onClickNavHealth(View view) {
        Intent intent = new Intent(ViewRodents.this, ViewHealth.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onClickNavOther(View view) {
        Intent intent = new Intent(ViewRodents.this, ViewOther.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



}