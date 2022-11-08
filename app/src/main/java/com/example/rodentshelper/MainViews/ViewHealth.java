package com.example.rodentshelper.MainViews;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.Medicaments.ViewMedicaments;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.Rodent.AdapterRodents;
import com.example.rodentshelper.ROOM.Rodent.AddRodents;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;

import java.util.List;

public class ViewHealth extends AppCompatActivity {

    ImageView imageButtonVet1, imageButtonMed, imageButton3_health;
    TextView textView3_health;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);


        imageButtonVet1 = findViewById(R.id.imageButtonVet1);
        imageButtonMed = findViewById(R.id.imageButtonMed);

        imageButton3_health = findViewById(R.id.imageButton3_health);
        textView3_health = findViewById(R.id.textView3_health);
        imageButton3_health.setColorFilter(Color.WHITE);
        textView3_health.setTextColor(Color.WHITE);


        imageButtonVet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewVets();
            }
        });

        imageButtonMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMeds();
            }
        });





    }

    public void onClickNavHealth(View view) {}

    public void onClickHealth(View view)
    {
        Intent intent = new Intent(ViewHealth.this, ViewVets.class);
        startActivity(intent);
    }

    public void onClickNavRodent(View view)
    {
        Intent intent = new Intent(ViewHealth.this, ViewRodents.class);
        startActivity(intent);
    }



    public void viewVets()
    {
        Intent intent = new Intent(ViewHealth.this, ViewVets.class);
        startActivity(intent);
    }

    public void viewMeds()
    {
        Intent intent = new Intent(ViewHealth.this, ViewMedicaments.class);
        startActivity(intent);
    }

    public void viewRodents()
    {
        finish();
        Intent intent = new Intent(ViewHealth.this, ViewRodents.class);
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