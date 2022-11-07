package com.example.rodentshelper.MainViews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.ROOM.Rodent.AddRodents;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.AdapterRodents;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;
import com.example.rodentshelper.ROOM.Vet.AdapterVets;
import com.example.rodentshelper.ROOM.Vet.VetModel;

import java.util.List;

public class ViewRodents extends AppCompatActivity {

    RecyclerView recyclerViewRodents;
    TextView textViewEmpty_rodent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rodents);



        recyclerViewRodents = findViewById(R.id.recyclerViewRodents);
        recyclerViewRodents.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRodents.setHasFixedSize(true);

        getRoomData();


        textViewEmpty_rodent = findViewById(R.id.textViewEmpty_rodent);

        if (getListRodent().isEmpty())
            textViewEmpty_rodent.setVisibility(View.VISIBLE);


      /*  if (rodentsModelClassList.size() > 0) {
            RodentsAdapterClass rodentsAdapterClass = new RodentsAdapterClass(rodentsModelClassList, ViewRodents.this);
            recyclerViewRodents.setAdapter(rodentsAdapterClass);
        }
        else {
            Toast.makeText(this, "Nie ma nic w bazie", Toast.LENGTH_SHORT).show();
            System.out.println("NIE MA NIC W BAZIE");
        }*/

    }

    public void addNewRodent(android.view.View view)
    {
        //1 = nowy
        FlagSetup.setFlagRodentAdd(1);
        Intent intent = new Intent(ViewRodents.this, AddRodents.class);
        startActivity(intent);
    }

    public void onClickHealth(android.view.View view)
    {
        Intent intent = new Intent(ViewRodents.this, ViewHealth.class);
        startActivity(intent);
    }

    public List getListRodent(){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO rodentDao = db.dao();

        List<RodentModel> rodentModel = rodentDao.getAllRodents();
        return rodentModel;
    }


    public void getRoomData()
    {
        recyclerViewRodents = findViewById(R.id.recyclerViewRodents);
        recyclerViewRodents.setLayoutManager(new LinearLayoutManager(this));

        AdapterRodents adapter = new AdapterRodents(getListRodent());

        recyclerViewRodents.setAdapter(adapter);
    }

}