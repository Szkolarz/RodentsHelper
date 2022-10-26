package com.example.rodentshelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodentshelper.SQL.DBHelperVet;

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

        DBHelperVet databaseHelper = new DBHelperVet(this);
        List<VetsModelClass> vetModelClasses = databaseHelper.getVetList();

        if (vetModelClasses.size() > 0) {
            VetsAdapterClass vetsAdapterClass = new VetsAdapterClass(vetModelClasses, ViewVets.this);
            recyclerViewVets.setAdapter(vetsAdapterClass);
        }
        else {
            Toast.makeText(this, "Nie ma nic w bazie", Toast.LENGTH_SHORT).show();
            System.out.println("NIE MA NIC W BAZIE");
        }

    }

    public void addNewVet(android.view.View view)
    {
        FlagSetup.setFlagVetAdd(1);
        final Context context = this;
        Intent intent = new Intent(context, AddVets.class);
        startActivity(intent);
        finish();
    }
}