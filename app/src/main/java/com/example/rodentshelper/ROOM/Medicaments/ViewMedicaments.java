package com.example.rodentshelper.ROOM.Medicaments;

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
import com.example.rodentshelper.ROOM.DAOMedicaments;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.Visits.ViewVisits;
import com.example.rodentshelper.ROOM._MTM._RodentMed.MedicamentWithRodentsCrossRef;


import java.util.List;

public class ViewMedicaments extends AppCompatActivity {

    RecyclerView recyclerView;
    Button buttonAddRecord;

    TextView textViewEmpty_med, textView3_health, textView1_rodent;

    private AppDatabase getAppDatabase () {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        return db;
    }

    private DAOMedicaments getDaoMedicaments () {
        DAOMedicaments daoMedicaments = getAppDatabase().daoMedicaments();
        return daoMedicaments;
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

        if (FlagSetup.getFlagIsFromHealth() == true) {
            textView3_health = findViewById(R.id.textView3_health);
            imageButton3_health.setColorFilter(Color.WHITE);
            textView3_health.setTextColor(Color.WHITE);
        }
        if (FlagSetup.getFlagIsFromHealth() == false) {
            FlagSetup.setFlagMedAdd(2);
            textView1_rodent = findViewById(R.id.textView1_rodent);
            imageButton1_rodent.setColorFilter(Color.WHITE);
            textView1_rodent.setTextColor(Color.WHITE);
        }


        buttonAddRecord = findViewById(R.id.buttonAddRecord);

        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getRoomData();

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





    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewHealth();
        }
        return super.onKeyDown(keyCode, event);
    }*/


    public List getListMedicament(){

        List<MedicamentWithRodentsCrossRef> medicamentModel = null;

        if (FlagSetup.getFlagMedAdd() == 2) {
            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            medicamentModel = getDaoMedicaments().getMedsWithRodentsWhereIdRodent(prefsGetRodentId.getInt("rodentId", 0));
        }
        else {
            medicamentModel = getDaoMedicaments().getMedsWithRodents();
            FlagSetup.setFlagMedAdd(1);
        }
        /** !!! **/
        //db.close();

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