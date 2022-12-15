package com.example.rodentshelper.ROOM.Weights;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.example.rodentshelper.MainViews.ViewEncyclopedia;
import com.example.rodentshelper.MainViews.ViewHealth;
import com.example.rodentshelper.MainViews.ViewOther;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAOWeight;
import com.example.rodentshelper.ROOM.DateFormat;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.ROOM.Vet.ViewVets;
import com.example.rodentshelper.ROOM._MTM._RodentWeight.RodentWithWeights;
import com.github.mikephil.charting.charts.LineChart;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class WeightView extends Activity {

    RecyclerView recyclerView;

    EditText editTextWeight;
    TextView textViewDate, textViewDateWeight_hidden, textViewInfo_weight;
    Button buttonAdd_weight, buttonSaveEdit_weight;
    ImageButton imageButtonDate_weight, imageButtonQuestion_weight;
    LineChart lineChart_weight;


    private DatePickerDialog.OnDateSetListener dateSetListener;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight);

        ImageButton imageButton1_rodent;
        TextView textView1_rodent;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        textView1_rodent = findViewById(R.id.textView1_rodent);
        imageButton1_rodent.setColorFilter(Color.WHITE);
        textView1_rodent.setTextColor(Color.WHITE);

        editTextWeight = findViewById(R.id.editTextWeight);
        textViewDate = findViewById(R.id.textViewDate);
        textViewInfo_weight = findViewById(R.id.textViewInfo_weight);

        textViewDateWeight_hidden = findViewById(R.id.textViewDateWeight_hidden);

        buttonAdd_weight = findViewById(R.id.buttonAdd_weight);
        buttonSaveEdit_weight = findViewById(R.id.buttonSaveEdit_weight);
        imageButtonDate_weight = findViewById(R.id.imageButtonDate_weight);
        imageButtonQuestion_weight = findViewById(R.id.imageButtonQuestion_weight);

        lineChart_weight = findViewById(R.id.lineChart_weight);



        if (FlagSetup.getFlagWeightAdd() == 0) {
            buttonAdd_weight.setVisibility(View.GONE);
            buttonSaveEdit_weight.setVisibility(View.VISIBLE);
            String weightKey = getIntent().getStringExtra("weightKey");
            String dateKey = getIntent().getStringExtra("dateKey");
            imageButtonDate_weight.setEnabled(false);

            System.out.println(dateKey);

            editTextWeight.setText(weightKey);
            textViewDate.setText(DateFormat.formatDate(java.sql.Date.valueOf(dateKey)));
            textViewDateWeight_hidden.setText(dateKey);

        } else {
            Date dateGet = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = df.format(dateGet);
            textViewDateWeight_hidden.setText(formattedDate);
            textViewDate.setText(DateFormat.formatDate(java.sql.Date.valueOf(formattedDate)));
        }


        /** Setting up chart */
        WeightChart weightChart = new WeightChart();
        weightChart.runChart(lineChart_weight, getApplicationContext());




        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOWeight daoWeight = db.daoWeight();




        SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
        List<RodentWithWeights> theLastWeight = daoWeight.getLastWeightByRodentId(prefsGetRodentId.getInt("rodentId", 0));

        AgeCalculator ageCalculator = new AgeCalculator();

        try {

            Date birth = java.sql.Date.valueOf( theLastWeight.get(0).rodents.get(0).getBirth().toString());

            List<Integer> listAge = new ArrayList<>();
            listAge = ageCalculator.calculateAge(birth);
            //list.get(0) = days list.get(1) = months; list.get(2) = years


            WeightPetHealthInfo weightPetHealthInfo = new WeightPetHealthInfo();
            weightPetHealthInfo.getPreference(getApplicationContext(), listAge.get(2), listAge.get(1), listAge.get(0),
                    theLastWeight.get(0).weightModel.getWeight(), textViewInfo_weight);

        } catch (IndexOutOfBoundsException e) {
            System.out.println(e);
        }

        getRoomData();

        imageButtonDate_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDateClick();
            }
        });

        imageButtonQuestion_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeightPetHealthInfo weightPetHealthInfo = new WeightPetHealthInfo();
                weightPetHealthInfo.tableInfo(WeightView.this);
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

        buttonSaveEdit_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEditWeight();
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

    public void saveEditWeight() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOWeight daoWeight = db.daoWeight();

        SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
        Integer idKey = Integer.parseInt(getIntent().getStringExtra("idKey"));

        daoWeight.updateWeightById(idKey, Integer.valueOf(editTextWeight.getText().toString()),
                java.sql.Date.valueOf(textViewDateWeight_hidden.getText().toString()));

        FlagSetup.setFlagWeightAdd(1);


        viewWeight();
        test();
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
        Intent intent = new Intent(WeightView.this, WeightView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }


    public List getListWeight(){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOWeight daoWeight = db.daoWeight();

        SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);

        List<RodentWithWeights> weightModel = daoWeight.getRodentWithWeights(prefsGetRodentId.getInt("rodentId", 0));
        return weightModel;
    }


    public void test (){

        recyclerView.getAdapter().notifyDataSetChanged();
    }


    public void getRoomData()
    {
        recyclerView = findViewById(R.id.recyclerView_weight);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterWeights adapter = new AdapterWeights(getListWeight());

        recyclerView.setAdapter(adapter);
    }


}
