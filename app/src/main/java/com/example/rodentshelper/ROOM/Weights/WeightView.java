package com.example.rodentshelper.ROOM.Weights;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAO;
import com.example.rodentshelper.ROOM.DAONotes;
import com.example.rodentshelper.ROOM.DAOWeight;
import com.example.rodentshelper.ROOM.DateFormat;
import com.example.rodentshelper.ROOM.Notes.AdapterNotes;
import com.example.rodentshelper.ROOM.Notes.NotesModel;
import com.example.rodentshelper.ROOM.Notes.ViewNotes;
import com.example.rodentshelper.ROOM.Rodent.AddRodents;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.ROOM._MTM.RodentWeight.RodentWithWeights;
import com.example.rodentshelper.ROOM._MTM.RodentWithNotes;
import com.example.rodentshelper.ROOM._MTM.VetWithRodentsCrossRef;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class WeightView extends Activity {

    RecyclerView recyclerView;

    EditText editTextWeight;
    TextView textViewDate, textViewDateWeight_hidden;
    Button buttonAdd_weight;
    ImageButton imageButtonDate_weight;
    LineChart lineChart_weight;


    private DatePickerDialog.OnDateSetListener dateSetListener;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight);

        editTextWeight = findViewById(R.id.editTextWeight);
        textViewDate = findViewById(R.id.textViewDate);

        textViewDateWeight_hidden = findViewById(R.id.textViewDateWeight_hidden);

        buttonAdd_weight = findViewById(R.id.buttonAdd_weight);
        imageButtonDate_weight = findViewById(R.id.imageButtonDate_weight);

        lineChart_weight = findViewById(R.id.lineChart_weight);
        lineChart_weight.setNoDataText("Brak danych. Potrzeba minimum dwóch pomiarów.");

        /** setting up chart */
        WeightChart weightChart = new WeightChart();
        weightChart.runChart(lineChart_weight, getApplicationContext());



        Date dateGet = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(dateGet);
        textViewDateWeight_hidden.setText(formattedDate);
        textViewDate.setText(DateFormat.formatDate(java.sql.Date.valueOf(formattedDate)));

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOWeight daoWeight = db.daoWeight();

        getRoomData();

        imageButtonDate_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDateClick();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;

                String date = (year + "-" + month + "-" + day);
                textViewDateWeight_hidden.setText(date);
                textViewDate.setText(DateFormat.formatDate(java.sql.Date.valueOf(date)));
            }
        };


        buttonAdd_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveWeight();
            }
        });


    }

    private void onDateClick() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(WeightView.this,
                android.R.style.Theme_Holo_Dialog, dateSetListener, year, month, day);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void saveWeight() {

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOWeight daoWeight = db.daoWeight();

        SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);


        if (daoWeight.isDateTheSame(prefsGetRodentId.getInt("rodentId", 0),
                java.sql.Date.valueOf(textViewDateWeight_hidden.getText().toString())) == true) {
            Alerts alert = new Alerts();
            alert.simpleError("Data już istnieje", "Waga z taką datą już istnieje. Można dodawać maksymalnie jeden pomiar w ciągu danego dnia.\n\n" +
                    "Jeśli chcesz zedytować pomiar w tym dniu, znajdź go w liście po prawej stronie i kliknij przycisk edycji.", this);
        } else {
            if (editTextWeight.getText().toString().equals("")) {
                Alerts alert = new Alerts();
                alert.alertLackOfData("Należy wpisać wagę pupila", this);
            } else {

                daoWeight.insertRecordWeight(new WeightModel(prefsGetRodentId.getInt("rodentId", 0), Integer.valueOf(editTextWeight.getText().toString()),
                        java.sql.Date.valueOf(textViewDateWeight_hidden.getText().toString())));
                System.out.println("DODANO");

                viewWeight();
            }
        }
    }






    private void viewWeight() {
        finish();
        startActivity(new Intent(getApplicationContext(), WeightView.class));
    }

    private void viewRodents() {
        finish();
        startActivity(new Intent(getApplicationContext(), ViewRodents.class));
    }



    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            viewWeight();
        }
        return super.onKeyDown(keyCode, event);
    }*/

    public List getListWeight(){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOWeight daoWeight = db.daoWeight();

        SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);

        List<RodentWithWeights> weightModel = daoWeight.getRodentWithWeights(prefsGetRodentId.getInt("rodentId", 0));
        return weightModel;
    }




    public void getRoomData()
    {
        recyclerView = findViewById(R.id.recyclerView_weight);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterWeights adapter = new AdapterWeights(getListWeight());

        recyclerView.setAdapter(adapter);
    }

}