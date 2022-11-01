package com.example.rodentshelper.MainViews;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.AddRodents;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.AdapterRodents;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;

import java.util.List;

public class ViewRodents extends AppCompatActivity {

    RecyclerView recyclerViewRodents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rodents);



        recyclerViewRodents = findViewById(R.id.recyclerViewRodents);
        recyclerViewRodents.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRodents.setHasFixedSize(true);

        getroomdata();


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
        FlagSetup.setFlagRodentAdd(1);
        Intent intent = new Intent(ViewRodents.this, AddRodents.class);
        startActivity(intent);
    }

    public void onClickVet(android.view.View view)
    {
        Intent intent = new Intent(ViewRodents.this, ViewVets.class);
        startActivity(intent);
    }


    public void getroomdata()
    {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO rodentDao = db.dao();

        recyclerViewRodents=findViewById(R.id.recyclerViewRodents);
        recyclerViewRodents.setLayoutManager(new LinearLayoutManager(this));

        List<RodentModel> rodentModel = rodentDao.getAllRodents();

        AdapterRodents adapter=new AdapterRodents(rodentModel);
        recyclerViewRodents.setAdapter(adapter);
    }

}