package com.example.rodentshelper.MainViews;

import android.content.Intent;
import android.os.Bundle;
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

    ImageView imageButtonVet1, imageButtonMed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);


        imageButtonVet1 = findViewById(R.id.imageButtonVet1);
        imageButtonMed = findViewById(R.id.imageButtonMed);


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


    public void onClickHealth(View view)
    {
        Intent intent = new Intent(ViewHealth.this, ViewVets.class);
        startActivity(intent);
    }

    public void onClickRodent(View view)
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






}