package com.example.rodentshelper.ROOM.Notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.MainViews.ViewEncyclopedia;
import com.example.rodentshelper.MainViews.ViewHealth;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotes;
import com.example.rodentshelper.ROOM._MTM._RodentNotes.RodentWithNotes;

import java.util.List;

public class ViewNotes extends AppCompatActivity {

    RecyclerView recyclerView;
    Button buttonAddRecord;

    TextView textViewEmpty, textView1_rodent;
    ImageView imageButton1_rodent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recycler);

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        textView1_rodent = findViewById(R.id.textView1_rodent);
        imageButton1_rodent.setColorFilter(Color.WHITE);
        textView1_rodent.setTextColor(Color.WHITE);

        buttonAddRecord = findViewById(R.id.buttonAddRecord);

        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getRoomData();

        textViewEmpty = findViewById(R.id.textViewEmptyGlobal);

        if (getListNotes().isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
            textViewEmpty.setText("Nie ma żadnych pozycji w bazie danych. Aby dodać nową notatkę, kliknij przycisk z plusikiem na górze ekranu.");
        }

        buttonAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNote();
            }
        });
    }

    public void addNewNote()
    {
        //1 = nowy
        FlagSetup.setFlagNotesAdd(1);
        final Context context = this;
        Intent intent = new Intent(context, AddNotes.class);
        startActivity(intent);
    }



    public void onClickNavHealth(View view)
    {
        viewHealth();
    }

    public void onClickNavRodent(View view)
    {
        viewRodents();
    }

    public void onClickNavEncyclopedia(View view)
    {
        viewEncyclopedia();
    }

    public void onClickNavOther(View view)
    {
        viewOther();
    }

    public void viewRodents() {
        Intent intent = new Intent(ViewNotes.this, ViewRodents.class);
        startActivity(intent);
    }

    public void viewHealth() {
        Intent intent = new Intent(ViewNotes.this, ViewHealth.class);
        startActivity(intent);
    }

    public void viewEncyclopedia() {
        Intent intent = new Intent(ViewNotes.this, ViewEncyclopedia.class);
        startActivity(intent);
    }

    public void viewOther() {
        Intent intent = new Intent(ViewNotes.this, ViewHealth.class);
        startActivity(intent);
    }



    public List getListNotes(){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAONotes dao = db.daoNotes();


        SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);

        List<RodentWithNotes> notesModel = dao.getRodentWithNotes(prefsGetRodentId.getInt("rodentId", 0));
        return notesModel;
    }


    public void getRoomData()
    {
        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterNotes adapter = new AdapterNotes(getListNotes());

        recyclerView.setAdapter(adapter);
    }

}