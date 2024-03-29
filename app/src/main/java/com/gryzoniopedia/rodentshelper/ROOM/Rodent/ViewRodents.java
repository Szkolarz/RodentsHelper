package com.gryzoniopedia.rodentshelper.ROOM.Rodent;

import android.app.Activity;
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
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.gryzoniopedia.rodentshelper.Alerts;
import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.Methods.AddEditRodents;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.Methods.RodentsDrawer;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.Methods.RodentsFillList;

import java.util.List;

public class ViewRodents extends AppCompatActivity  {

    private RecyclerView recyclerView;


    public Activity q () {

        return ViewRodents.this;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recycler_rodents);




        Toolbar toolbar = findViewById(R.id.app_bar_rodents);
        toolbar.setTitle("");


        TextView textViewYourRodent = findViewById(R.id.textViewYourRodent);
        SharedPreferences prefsFirstStart = this.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        if (prefsFirstStart.getInt("prefsFirstStart", 0) == 1)
            textViewYourRodent.setText("Twoje świnki morskie");
        if (prefsFirstStart.getInt("prefsFirstStart", 0) == 2)
            textViewYourRodent.setText("Twoje szczury");
        if (prefsFirstStart.getInt("prefsFirstStart", 0) == 3)
            textViewYourRodent.setText("Twoje szynszyle");

        SharedPreferences prefsFirstStartTip = getSharedPreferences("prefsFirstStartTip", MODE_PRIVATE);
        if (prefsFirstStartTip.getInt("prefsFirstStartTip", 0) == 0) {
            firstStartTip();
        }


        setSupportActionBar(toolbar);

        RodentsDrawer rodentsDrawer = new RodentsDrawer(ViewRodents.this, toolbar);

        rodentsDrawer.activityGlobal = ViewRodents.this;

        rodentsDrawer.createDrawer();



        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);

        TextView textView1_rodent = findViewById(R.id.textView1_rodent);
        imageButton1_rodent.setColorFilter(Color.WHITE);
        textView1_rodent.setTextColor(Color.WHITE);

        Button buttonAddRecord = findViewById(R.id.buttonAddRecord);

        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getRoomData();


        TextView textViewEmpty_rodent = findViewById(R.id.textViewEmptyGlobal);

        if (getListRodent().isEmpty()) {
            textViewEmpty_rodent.setVisibility(View.VISIBLE);
            textViewEmpty_rodent.setText("Nie ma żadnych pozycji w bazie danych. Aby dodać nowego pupila, kliknij przycisk z plusikiem na górze ekranu.");
        }

            buttonAddRecord.setOnClickListener(view -> addNewRodent());


      /*  if (rodentsModelClassList.size() > 0) {
            RodentsAdapterClass rodentsAdapterClass = new RodentsAdapterClass(rodentsModelClassList, ViewRodents.this);
            recyclerViewRodents.setAdapter(rodentsAdapterClass);
        }
        else {
            Toast.makeText(this, "Nie ma nic w bazie", Toast.LENGTH_SHORT).show();
            System.out.println("NIE MA NIC W BAZIE");
        }*/
        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Dotknij ponownie, aby zamknąć aplikację", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }


    public void addNewRodent()
    {
        //1 = nowy
        FlagSetup.setFlagRodentAdd(1);
        Intent intent = new Intent(ViewRodents.this, AddEditRodents.class);
        startActivity(intent);
        finish();
    }

    public List getListRodent(){
        RodentsFillList rodentsFillList = new RodentsFillList();
        return rodentsFillList.getList(this);
    }



    private void getRoomData()
    {
        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterRodents adapter = new AdapterRodents(getListRodent());

        recyclerView.setAdapter(adapter);
    }


    private void firstStartTip () {
        SharedPreferences prefsFirstStartTip = getSharedPreferences("prefsFirstStartTip", MODE_PRIVATE);

        SharedPreferences.Editor prefsEitorFirstStartTip = prefsFirstStartTip.edit();
        prefsEitorFirstStartTip.putInt("prefsFirstStartTip", 1);
        prefsEitorFirstStartTip.apply();

        Alerts alert = new Alerts();

        alert.simpleInfo("Witaj w aplikacji!", "Aby zacząć, możesz dodać zwierzę na tym ekranie, by " +
                "od razu rozszerzyć możliwości aplikacji.\n\n" +
                "Nie jest to jednak wymagane; za pomocą przycisków u dołu ekranu możesz również przenieść " +
                "się do pozostałych modułów, zawartych w aplikacji.", ViewRodents.this);
    }


}