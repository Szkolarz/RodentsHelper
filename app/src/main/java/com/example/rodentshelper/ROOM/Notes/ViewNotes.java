package com.example.rodentshelper.ROOM.Notes;

import android.app.DatePickerDialog;
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
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.MainViews.ViewEncyclopedia;
import com.example.rodentshelper.MainViews.ViewHealth;
import com.example.rodentshelper.MainViews.ViewOther;
import com.example.rodentshelper.ROOM.DateFormat;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAONotes;
import com.example.rodentshelper.ROOM._MTM._RodentNotes.RodentWithNotes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ViewNotes extends AppCompatActivity {

    RecyclerView recyclerView;
    Button buttonAddRecord;

    TextView textViewEmpty, textView1_rodent, textViewDate_notes, textViewDateHidden_notes;
    private DatePickerDialog.OnDateSetListener dateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recycler);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Notatki");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());

        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);

        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());

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
            textViewEmpty.setText("Nie ma żadnych pozycji w bazie danych. Aby dodać nową notatkę, " +
                    "kliknij przycisk z plusikiem na górze ekranu.");
        }

        textViewDate_notes = findViewById(R.id.textViewDate_notes);
        textViewDateHidden_notes = findViewById(R.id.textViewDateHidden_notes);


        buttonAddRecord.setOnClickListener(view -> addNewNote());
    }

    public void addNewNote()
    {
        //1 = nowy
        FlagSetup.setFlagNotesAdd(1);
        final Context context = this;
        Intent intent = new Intent(context, AddNotes.class);
        startActivity(intent);
    }






    public List getListNotes(){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAONotes dao = db.daoNotes();


        SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);

        //List<RodentWithNotes> notesModel = dao.getRodentWithNotes(prefsGetRodentId.getInt("rodentId", 0));
        return dao.getRodentWithNotes(prefsGetRodentId.getInt("rodentId", 0));
    }


    public void getRoomData()
    {
        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterNotes adapter = new AdapterNotes(getListNotes());

        recyclerView.setAdapter(adapter);
    }


}