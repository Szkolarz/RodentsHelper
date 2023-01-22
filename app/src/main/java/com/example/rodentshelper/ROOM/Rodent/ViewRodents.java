package com.example.rodentshelper.ROOM.Rodent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.example.rodentshelper.DatabaseManagement.ActivityDatabaseManagement;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.MainViews.FirstStart;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAORodents;

import java.util.List;
import java.util.Objects;

public class ViewRodents extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.choose_rodent:
                Intent intent = new Intent(ViewRodents.this, FirstStart.class);
                startActivity(intent);
                finish();
                break;
            case R.id.database_management_name:
                Intent intentDb = new Intent(ViewRodents.this, ActivityDatabaseManagement.class);
                startActivity(intentDb);
                finish();
                break;
            case R.id.about_app:
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recycler_rodents);


        Toolbar toolbar = findViewById(R.id.app_bar_rodents);
        toolbar.setTitle("");


        TextView textViewYourRodent = findViewById(R.id.textViewYourRodent);
        SharedPreferences prefsFirstStart = this.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        if (prefsFirstStart.getInt("prefsFirstStart", 0) == 1)
            textViewYourRodent.setText("Twoje świnki morskie");
        if (prefsFirstStart.getInt("prefsFirstStart", 0) == 2)
            textViewYourRodent.setText("Twoje szczury");
        if (prefsFirstStart.getInt("prefsFirstStart", 0) == 3)
            textViewYourRodent.setText("Twoje szynszyle");



      // toolbar.setNavigationIcon(R.drawable.id_encyclopedia);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_hamburger);


        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_hamburger);
        toolbar.setOverflowIcon(drawable);

        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);

        TextView textView1_rodent = findViewById(R.id.textView1_rodent);
        imageButton1_rodent.setColorFilter(Color.WHITE);
        textView1_rodent.setTextColor(Color.WHITE);

        Button buttonAddRecord = findViewById(R.id.buttonAddRecord);

        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getRoomData(ViewRodents.this);


        TextView textViewEmpty_rodent = findViewById(R.id.textViewEmptyGlobal);

        if (getListRodent(ViewRodents.this).isEmpty()) {
            textViewEmpty_rodent.setVisibility(View.VISIBLE);
            textViewEmpty_rodent.setText("Nie ma żadnych pozycji w bazie danych. Aby dodać nowego pupila, kliknij przycisk z plusikiem na górze ekranu.");
        }

            buttonAddRecord.setOnClickListener(view -> addNewRodent());


      /*  if (rodentsModelClassList.size() > 0) {
            RodentsAdapterClass rodentsAdapterClass = new RodentsAdapterClass(rodentsModelClassList, ViewRodents.this);
            recyclerViewRodents.setAdapter(rodentsAdapterClass);
        }
        else {
            Toast.makeText(this, "Nie ma nic w bazie", Toast.LENGTH_SHORT).show();
            System.out.println("NIE MA NIC W BAZIE");
        }*/
        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Dotknij ponownie, aby zamknąć aplikację", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }


    public void addNewRodent()
    {
        //1 = nowy
        FlagSetup.setFlagRodentAdd(1);
        Intent intent = new Intent(ViewRodents.this, AddRodents.class);
        startActivity(intent);
        finish();
    }

    public List getListRodent(ViewRodents viewRodents){
        SharedPreferences prefsFirstStart = viewRodents.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAORodents daoRodents = db.daoRodents();

        List<RodentModel> rodentModel = daoRodents.getAllRodents(prefsFirstStart.getInt("prefsFirstStart", 0));
        db.close();

        return rodentModel;
    }


    public void getRoomData(ViewRodents viewRodents)
    {
        recyclerView = findViewById(R.id.recyclerViewGlobal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterRodents adapter = new AdapterRodents(getListRodent(viewRodents));

        recyclerView.setAdapter(adapter);
    }
}