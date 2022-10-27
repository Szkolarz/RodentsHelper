package com.example.rodentshelper.MainViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodentshelper.AddVets;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.example.rodentshelper.SQL.DBHelperVet;
import com.example.rodentshelper.ClassAdapters.VetsAdapterClass;
import com.example.rodentshelper.ClassModels.VetsModelClass;

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

}