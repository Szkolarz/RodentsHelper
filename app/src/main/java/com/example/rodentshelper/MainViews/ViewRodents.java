package com.example.rodentshelper.MainViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rodentshelper.AddRodents;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ClassAdapters.RodentsAdapterClass;
import com.example.rodentshelper.ClassModels.RodentsModelClass;
import com.example.rodentshelper.SQL.DBHelperAnimal;

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

        DBHelperAnimal databaseHelper = new DBHelperAnimal(this);
        List<RodentsModelClass> rodentsModelClassList = databaseHelper.getRodentsLis();

        if (rodentsModelClassList.size() > 0) {
            RodentsAdapterClass rodentsAdapterClass = new RodentsAdapterClass(rodentsModelClassList, ViewRodents.this);
            recyclerViewRodents.setAdapter(rodentsAdapterClass);
        }
        else {
            Toast.makeText(this, "Nie ma nic w bazie", Toast.LENGTH_SHORT).show();
            System.out.println("NIE MA NIC W BAZIE");
        }

    }

    public void addNewRodent(android.view.View view)
    {
        Intent intent = new Intent(ViewRodents.this, AddRodents.class);
        startActivity(intent);
    }

    public void onClickVet(android.view.View view)
    {
        Intent intent = new Intent(ViewRodents.this, ViewVets.class);
        startActivity(intent);
    }



}