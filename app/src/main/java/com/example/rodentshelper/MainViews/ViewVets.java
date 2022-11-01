package com.example.rodentshelper.MainViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.AddVets;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.Vet.AdapterVets;
import com.example.rodentshelper.ROOM.Vet.VetModel;

import java.util.List;

public class ViewVets extends AppCompatActivity {

    RecyclerView recyclerViewVets;
    Button buttonAddVets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vets);


        buttonAddVets = findViewById(R.id.buttonAddVets);

        recyclerViewVets = findViewById(R.id.recyclerViewVets);
        recyclerViewVets.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewVets.setHasFixedSize(true);

        getRoomData();



    }

    public void addNewVet(android.view.View view)
    {
        FlagSetup.setFlagVetAdd(1);
        final Context context = this;
        Intent intent = new Intent(context, AddVets.class);
        startActivity(intent);
        finish();
    }

    public void viewRodents() {
        Intent intent = new Intent(ViewVets.this, ViewRodents.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewRodents();
        }
        return super.onKeyDown(keyCode, event);
    }


    public void getRoomData()
    {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO vetDao = db.dao();

        recyclerViewVets=findViewById(R.id.recyclerViewVets);
        recyclerViewVets.setLayoutManager(new LinearLayoutManager(this));

        List<VetModel> vetModel = vetDao.getAllVets();

        AdapterVets adapter=new AdapterVets(vetModel);
        recyclerViewVets.setAdapter(adapter);
    }

}