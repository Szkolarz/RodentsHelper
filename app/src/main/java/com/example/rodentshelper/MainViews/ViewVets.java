package com.example.rodentshelper.MainViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.ROOM.Vet.AddVets;
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
    TextView textViewEmpty_vet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vets);


        buttonAddVets = findViewById(R.id.buttonAddVets);

        recyclerViewVets = findViewById(R.id.recyclerViewVets);
        recyclerViewVets.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewVets.setHasFixedSize(true);

        getRoomData();

        textViewEmpty_vet = findViewById(R.id.textViewEmpty_vet);

        if (getListVet().isEmpty())
            textViewEmpty_vet.setVisibility(View.VISIBLE);

    }

    public void addNewVet(android.view.View view)
    {
        //1 = nowy
        FlagSetup.setFlagVetAdd(1);
        final Context context = this;
        Intent intent = new Intent(context, AddVets.class);
        startActivity(intent);
        finish();
    }

    public void onClickViewRodents(android.view.View view)
    {
        viewRodents();
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


    public List getListVet(){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAO vetDao = db.dao();

        List<VetModel> vetModel = vetDao.getAllVets();
        return vetModel;
    }


    public void getRoomData()
    {
        recyclerViewVets=findViewById(R.id.recyclerViewVets);
        recyclerViewVets.setLayoutManager(new LinearLayoutManager(this));

        AdapterVets adapter=new AdapterVets(getListVet());

        recyclerViewVets.setAdapter(adapter);
    }

}