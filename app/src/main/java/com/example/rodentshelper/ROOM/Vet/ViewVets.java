package com.example.rodentshelper.ROOM.Vet;

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

import com.example.rodentshelper.MainViews.ViewHealth;
import com.example.rodentshelper.MainViews.ViewOther;
import com.example.rodentshelper.ROOM.DAOVets;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM._MTM.VetWithRodentsCrossRef;

import java.util.List;

public class ViewVets extends AppCompatActivity {

    RecyclerView recyclerView;
    Button buttonAddRecord;
    TextView textViewEmpty_vet, textView3_health, textView1_rodent;
    ImageView imageButton3_health, imageButton1_rodent;

    private AppDatabase getAppDatabase () {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        return db;
    }

    private DAOVets getDaoVets () {
        DAOVets daoVets = getAppDatabase().daoVets();
        return daoVets;
    }

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
            FlagSetup.setFlagVetAdd(2);
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

        textViewEmpty_vet = findViewById(R.id.textViewEmptyGlobal);

        if (getListVet().isEmpty()) {
            textViewEmpty_vet.setVisibility(View.VISIBLE);
            textViewEmpty_vet.setText("Nie ma żadnych pozycji w bazie danych. Aby dodać nowego weterynarza, kliknij przycisk z plusikiem na górze ekranu.");
        }
        buttonAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewVet();
            }
        });
    }

    public void addNewVet()
    {
        if (FlagSetup.getFlagIsFromHealth() == true)
            FlagSetup.setFlagVetAdd(1);
        else
            FlagSetup.setFlagVetAdd(2);
        //1 = nowy
        //FlagSetup.setFlagVetAdd(1);
        final Context context = this;
        Intent intent = new Intent(context, AddVets.class);
        startActivity(intent);
    }

    public void onClickViewRodents(android.view.View view)
    {
        viewRodents();
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

    public void viewRodents() {
        Intent intent = new Intent(ViewVets.this, ViewRodents.class);
        startActivity(intent);
    }

    public void viewOther() {
        Intent intent = new Intent(ViewVets.this, ViewOther.class);
        startActivity(intent);
    }

    public void viewHealth() {
        Intent intent = new Intent(ViewVets.this, ViewHealth.class);
        startActivity(intent);
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewHealth();
        }
        return super.onKeyDown(keyCode, event);
    }*/


    public List getListVet(){


        List<VetWithRodentsCrossRef> vetModel = null;


        if (FlagSetup.getFlagVetAdd() == 2) {
            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            vetModel = getDaoVets().getVetsWithRodentsWhereIdRodent(prefsGetRodentId.getInt("rodentId", 0));
        }
        else {
            vetModel = getDaoVets().getVetsWithRodents();
            FlagSetup.setFlagVetAdd(1);
        }

        /*for (int i = 0; i < vetModel.size(); i++) {
            System.out.println(vetModel.get(i).rodents.get(i).getName()+ " ggh");
            System.out.println(d.get(i) + " aggah");
        }*/


      /*  List<RodentWithVets> rodentModel = null;

        rodentModel = dao.getaaa();*/


/*
        FlagSetup flagSetup = new FlagSetup();
        int flag = flagSetup.getFlagVetAdd();

        System.out.println(flag);
        System.out.println(flag);

        if (flag == 2) {
            SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
            vetModel = vetDao.getAllVetsByRodentId(prefsGetRodentId.getInt("rodentId", 0));
        }
        else {
            vetModel = vetDao.getAllVets();
            FlagSetup.setFlagVetAdd(1);
        }*/

        return vetModel;
    }


    public void getRoomData()
    {
        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterVets adapter = new AdapterVets(getListVet());

        recyclerView.setAdapter(adapter);
    }

}