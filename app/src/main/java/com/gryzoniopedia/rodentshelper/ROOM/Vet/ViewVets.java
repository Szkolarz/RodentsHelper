package com.gryzoniopedia.rodentshelper.ROOM.Vet;

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
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.Methods.RodentsFillList;
import com.gryzoniopedia.rodentshelper.ROOM.Vet.Methods.AddEditVets;
import com.gryzoniopedia.rodentshelper.ROOM.Vet.Methods.VetsFillList;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentVet.VetWithRodentsCrossRef;
import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAOVets;

import java.util.List;
import java.util.Objects;

public class ViewVets extends AppCompatActivity {

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

        if (FlagSetup.getFlagIsFromHealth()) {
            TextView textView3_health = findViewById(R.id.textView3_health);
            imageButton3_health.setColorFilter(Color.WHITE);
            textView3_health.setTextColor(Color.WHITE);
        } else {
            FlagSetup.setFlagVetAdd(2);
            TextView textView1_rodent = findViewById(R.id.textView1_rodent);
            imageButton1_rodent.setColorFilter(Color.WHITE);
            textView1_rodent.setTextColor(Color.WHITE);
        }

        Button buttonAddRecord = findViewById(R.id.buttonAddRecord);

        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getRoomData();

        TextView textViewEmpty_vet = findViewById(R.id.textViewEmptyGlobal);

        if (getListVet().isEmpty()) {
            textViewEmpty_vet.setVisibility(View.VISIBLE);
            textViewEmpty_vet.setText("Nie ma żadnych pozycji w bazie danych. Aby dodać nowego weterynarza, kliknij przycisk z plusikiem na górze ekranu.");
        }
        buttonAddRecord.setOnClickListener(view -> addNewVet());

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    public void addNewVet()
    {
        if (FlagSetup.getFlagIsFromHealth())
            FlagSetup.setFlagVetAdd(1);
        else
            FlagSetup.setFlagVetAdd(2);
        //1 = nowy
        //FlagSetup.setFlagVetAdd(1);
        final Context context = this;
        Intent intent = new Intent(context, AddEditVets.class);
        startActivity(intent);
        finish();
    }



    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewHealth();
        }
        return super.onKeyDown(keyCode, event);
    }*/


    public List getListVet(){
        VetsFillList vetsFillList = new VetsFillList();
        return vetsFillList.getList(this, toolbar);
    }


    public void getRoomData()
    {
        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterVets adapter = new AdapterVets(getListVet());

        recyclerView.setAdapter(adapter);
    }
}