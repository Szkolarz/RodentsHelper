package com.example.rodentshelper.ROOM.Medicaments;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.MainViews.ViewEncyclopedia;
import com.example.rodentshelper.MainViews.ViewHealth;
import com.example.rodentshelper.MainViews.ViewOther;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;


import java.util.List;

public class ViewMedicaments extends AppCompatActivity {

    RecyclerView recyclerView;
    Button buttonAddRecord;

    TextView textViewEmpty_med, textView3_health, textView1_rodent;
    ImageView imageButton3_health, imageButton1_rodent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recycler);

        if (FlagSetup.getFlagIsFromHealth() == true) {
            imageButton3_health = findViewById(R.id.imageButton3_health);
            textView3_health = findViewById(R.id.textView3_health);
            imageButton3_health.setColorFilter(Color.WHITE);
            textView3_health.setTextColor(Color.WHITE);
        }
        if (FlagSetup.getFlagIsFromHealth() == false) {
            imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
            textView1_rodent = findViewById(R.id.textView1_rodent);
            imageButton1_rodent.setColorFilter(Color.WHITE);
            textView1_rodent.setTextColor(Color.WHITE);
        }


        buttonAddRecord = findViewById(R.id.buttonAddRecord);

        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getRoomData();
        System.out.println("222");
        textViewEmpty_med = findViewById(R.id.textViewEmptyGlobal);

        if (getListMedicament().isEmpty()) {
            textViewEmpty_med.setVisibility(View.VISIBLE);
            textViewEmpty_med.setText("Nie ma żadnych pozycji w bazie danych. Aby dodać nowy lek, kliknij przycisk z plusikiem na górze ekranu.");
        }



        buttonAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewMedicament();
            }
        });

    }

    public void addNewMedicament()
    {
        //1 = new without relations
        //2 = new with static relation
        if (FlagSetup.getFlagIsFromHealth() == true)
            FlagSetup.setFlagMedAdd(1);
        else
            FlagSetup.setFlagMedAdd(2);

        final Context context = this;
        Intent intent = new Intent(context, AddMedicaments.class);
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

    public void onClickNavOther(View view)
    {
        viewOther();
    }

    public void onClickNavEncyclopedia(View view)
    {
        viewEncyclopedia();
    }

    public void viewRodents() {
        Intent intent = new Intent(ViewMedicaments.this, ViewRodents.class);
        startActivity(intent);
    }

    public void viewHealth() {
        Intent intent = new Intent(ViewMedicaments.this, ViewHealth.class);
        startActivity(intent);
    }

    public void viewEncyclopedia() {
        Intent intent = new Intent(ViewMedicaments.this, ViewEncyclopedia.class);
        startActivity(intent);
    }

    public void viewOther() {
        Intent intent = new Intent(ViewMedicaments.this, ViewOther.class);
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
        List<MedicamentModel> medicamentModel = null;

        if (FlagSetup.getFlagMedAdd() == 2) {
            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            medicamentModel = medDao.getAllMedsByRodentId(prefsGetRodentId.getInt("rodentId", 0));
        }
        else {
            medicamentModel = medDao.getAllMedicaments();
            FlagSetup.setFlagMedAdd(1);
        }
        db.close();

        System.out.println("111");

        return medicamentModel;
    }


    public void getRoomData()
    {
        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterMedicaments adapter = new AdapterMedicaments(getListMedicament());

        recyclerView.setAdapter(adapter);
    }

}