package com.example.rodentshelper.MainViews;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rodentshelper.Encyclopedia.InternetCheckEncyclopedia;
import com.example.rodentshelper.Encyclopedia.Treats.ViewTreats;
import com.example.rodentshelper.ROOM.Medicaments.ViewMedicaments;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.ROOM.Vet.ViewVets;
import com.example.rodentshelper.ROOM.Visits.ViewVisits;

public class ViewEncyclopedia extends AppCompatActivity {

    ImageView imageButtonGeneral, imageButtonFood, imageButtonSupply, imageButtonSound, imageButton2_encyclopedia;
    TextView textView2_encyclopedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encyclopedia);


        imageButtonGeneral = findViewById(R.id.imageButtonGeneral);
        imageButtonFood = findViewById(R.id.imageButtonFood);
        imageButtonSupply = findViewById(R.id.imageButtonSupply);
        imageButtonSound = findViewById(R.id.imageButtonSound);



        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        textView2_encyclopedia = findViewById(R.id.textView2_encyclopedia);
        imageButton2_encyclopedia.setColorFilter(Color.WHITE);
        textView2_encyclopedia.setTextColor(Color.WHITE);

        InternetCheckEncyclopedia internetCheckEncyclopedia = new InternetCheckEncyclopedia();
        internetCheckEncyclopedia.checkInternet(ViewEncyclopedia.this);

        imageButtonGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewVets();
            }
        });

        imageButtonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewTreats();
            }
        });

        imageButtonSupply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewVisits();
            }
        });

        imageButtonSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewVisits();
            }
        });






    }

    //public void onClickNavHealth(View view) {}

    public void onClickNavHealth(View view)
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewHealth.class);
        startActivity(intent);
    }

    public void onClickNavRodent(View view)
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewRodents.class);
        startActivity(intent);
    }

    public void onClickNavEncyclopedia(View view) {}

    public void onClickNavOther(View view) {}



    public void viewVets()
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewVets.class);
        startActivity(intent);
    }

    public void viewTreats()
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewTreats.class);
        startActivity(intent);
    }

    public void viewOther()
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewOther.class);
        startActivity(intent);
    }

    public void viewMeds()
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewMedicaments.class);
        startActivity(intent);
    }

    public void viewVisits()
    {
        Intent intent = new Intent(ViewEncyclopedia.this, ViewVisits.class);
        startActivity(intent);
    }

    public void viewRodents()
    {
        finish();
        Intent intent = new Intent(ViewEncyclopedia.this, ViewRodents.class);
        startActivity(intent);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewRodents();
        }
        return super.onKeyDown(keyCode, event);
    }


}