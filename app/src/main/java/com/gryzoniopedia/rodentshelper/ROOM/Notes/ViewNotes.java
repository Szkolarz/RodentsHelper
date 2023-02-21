package com.gryzoniopedia.rodentshelper.ROOM.Notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAONotes;
import com.gryzoniopedia.rodentshelper.ROOM.Notes.Methods.AddEditNotes;
import com.gryzoniopedia.rodentshelper.ROOM.Notes.Methods.NotesFillList;
import com.gryzoniopedia.rodentshelper.ROOM.Notes.Methods.NotesFilter;

import java.util.List;
import java.util.Objects;

public class ViewNotes extends AppCompatActivity {

    private RecyclerView recyclerView;


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar_notes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.info_notes) {
            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.custom_dialog_notes, null);

            NotesFilter notesFilter = new NotesFilter();
            notesFilter.setUpFilter(this, alertLayout);
        }

        return super.onOptionsItemSelected(item);
    }

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

        TextView textView1_rodent = findViewById(R.id.textView1_rodent);
        imageButton1_rodent.setColorFilter(Color.WHITE);
        textView1_rodent.setTextColor(Color.WHITE);

        Button buttonAddRecord = findViewById(R.id.buttonAddRecord);

        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getRoomData();

        TextView textViewEmpty = findViewById(R.id.textViewEmptyGlobal);

        if (getListNotes().isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
            textViewEmpty.setText("Nie ma żadnych pozycji w bazie danych. Aby dodać nową notatkę, " +
                    "kliknij przycisk z plusikiem na górze ekranu.");
        }
        buttonAddRecord.setOnClickListener(view -> addNewNote());
    }

    public void addNewNote()
    {
        //1 = nowy
        FlagSetup.setFlagNotesAdd(1);
        final Context context = this;
        Intent intent = new Intent(context, AddEditNotes.class);
        startActivity(intent);
        finish();
    }



    public List getListNotes(){
        NotesFillList notesFillList = new NotesFillList();
        return notesFillList.getList(getApplicationContext());
    }


    public void getRoomData()
    {
        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterNotes adapter = new AdapterNotes(getListNotes());

        recyclerView.setAdapter(adapter);
    }


}