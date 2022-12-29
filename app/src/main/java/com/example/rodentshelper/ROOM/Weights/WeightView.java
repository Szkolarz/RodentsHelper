package com.example.rodentshelper.ROOM.Weights;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
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
import com.example.rodentshelper.ROOM.Visits.ViewVisits;
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
    TextView textViewDate, textViewDateWeight_hidden, textViewInfo_weight, textViewChart_weight;
    Button buttonAdd_weight, buttonSaveEdit_weight;
    ImageButton imageButtonDate_weight, imageButtonQuestion_weight;
    LineChart lineChart_weight;
    LinearLayout linearLayoutChart_weight, linearLayoutChartInfo_weight;
    ScrollView scrollView_weight;


    private DatePickerDialog.OnDateSetListener dateSetListener;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight);

        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);

        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());


        TextView textView1_rodent;

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
        linearLayoutChart_weight = findViewById(R.id.linearLayoutChart_weight);
        linearLayoutChartInfo_weight = findViewById(R.id.linearLayoutChartInfo_weight);
        scrollView_weight = findViewById(R.id.scrollView_weight);



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
        weightChart.runChart(lineChart_weight, getBaseContext());

        List <WeightModel> weightModel = weightChart.getListWeightASC(getBaseContext());

        if (weightModel.size() < 2) {
            linearLayoutChartInfo_weight.setVisibility(View.VISIBLE);
            lineChart_weight.setVisibility(View.GONE);
            linearLayoutChart_weight.setVisibility(View.GONE);
        }


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



        editTextWeight.setOnTouchListener((v, event) -> {
            if(MotionEvent.ACTION_UP == event.getAction()) {
                Handler handler = new Handler();
                handler.postDelayed(() -> scrollView_weight.smoothScrollTo(0, 9999), 600);
            }
            return false;
        });



        imageButtonDate_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDateClick();
            }
        });

        imageButtonQuestion_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefsFirstStart = getApplicationContext().getSharedPreferences("prefsFirstStart", Context.MODE_PRIVATE);

                WeightPetHealthInfo weightPetHealthInfo = new WeightPetHealthInfo();
                if (prefsFirstStart.getInt("prefsFirstStart", 0) == 1)
                    weightPetHealthInfo.tableInfoChinchilla(WeightView.this); // do zmiany
                else if (prefsFirstStart.getInt("prefsFirstStart", 0) == 2)
                    weightPetHealthInfo.tableInfoChinchilla(WeightView.this); //do zmiany
                else if (prefsFirstStart.getInt("prefsFirstStart", 0) == 3)
                    weightPetHealthInfo.tableInfoChinchilla(WeightView.this);

                finish();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK && FlagSetup.getFlagWeightAdd() == 0)) {
            FlagSetup.setFlagWeightAdd(1);
            Intent intent = new Intent(WeightView.this, WeightView.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}
