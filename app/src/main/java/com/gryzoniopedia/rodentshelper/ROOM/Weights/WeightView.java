package com.gryzoniopedia.rodentshelper.ROOM.Weights;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.gryzoniopedia.rodentshelper.Alerts;
import com.gryzoniopedia.rodentshelper.DatabaseManagement.ActivityDatabaseManagement;
import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.MainViews.ViewEncyclopedia;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAOWeight;
import com.gryzoniopedia.rodentshelper.ROOM.DateFormat;
import com.gryzoniopedia.rodentshelper.ROOM._MTM._RodentWeight.RodentWithWeights;
import com.github.mikephil.charting.charts.LineChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class WeightView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editTextWeight;
    private TextView textViewDate, textViewDateWeight_hidden;
    private ScrollView scrollView_weight;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar_cogwheel, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cogwheel_global) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(WeightView.this);


            View inflateView;
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflateView = inflater.inflate(R.layout.btn_weight_options, null);

            dialog.setCustomTitle(inflateView);
            dialog.setOnCancelListener(dialog1 -> {
                reloadActivity();
            });

            SwitchCompat switch_chart = inflateView.findViewById(R.id.switch_chart);
            SwitchCompat switch_weight = inflateView.findViewById(R.id.switch_weight);

            SharedPreferences prefsChartInfo = getSharedPreferences("prefsChartInfo", MODE_PRIVATE);
            SharedPreferences prefsWeightInfo = getSharedPreferences("prefsWeightInfo", MODE_PRIVATE);


            if (prefsChartInfo.getBoolean("prefsChartInfo", true))
                switch_chart.setChecked(true);
            else
                switch_chart.setChecked(false);

            if (prefsWeightInfo.getBoolean("prefsWeightInfo", true))
                switch_weight.setChecked(true);
            else
                switch_weight.setChecked(false);


            switch_chart.setOnClickListener(v -> {
                if (switch_chart.isChecked()) {
                    SharedPreferences.Editor editorChartInfo = prefsChartInfo.edit();
                    editorChartInfo.putBoolean("prefsChartInfo", true);
                    editorChartInfo.apply();
                } else {
                    SharedPreferences.Editor editorChartInfo = prefsChartInfo.edit();
                    editorChartInfo.putBoolean("prefsChartInfo", false);
                    editorChartInfo.apply();
                }
            });

            switch_weight.setOnClickListener(v -> {
                if (switch_weight.isChecked()) {
                    SharedPreferences.Editor editorWeightInfo = prefsWeightInfo.edit();
                    editorWeightInfo.putBoolean("prefsWeightInfo", true);
                    editorWeightInfo.apply();
                } else {
                    SharedPreferences.Editor editorWeightInfo = prefsWeightInfo.edit();
                    editorWeightInfo.putBoolean("prefsWeightInfo", false);
                    editorWeightInfo.apply();
                }
            });
            AlertDialog alertDialog = dialog.show();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return super.onOptionsItemSelected(item);
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Ważenie");
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


        TextView textView1_rodent;

        textView1_rodent = findViewById(R.id.textView1_rodent);
        imageButton1_rodent.setColorFilter(Color.WHITE);
        textView1_rodent.setTextColor(Color.WHITE);

        editTextWeight = findViewById(R.id.editTextWeight);
        textViewDate = findViewById(R.id.textViewDate);
        TextView textViewInfo_weight = findViewById(R.id.textViewInfo_weight);



        textViewDateWeight_hidden = findViewById(R.id.textViewDateWeight_hidden);

        Button buttonAdd_weight = findViewById(R.id.buttonAdd_weight);
        Button buttonSaveEdit_weight = findViewById(R.id.buttonSaveEdit_weight);
        ImageButton imageButtonDate_weight = findViewById(R.id.imageButtonDate_weight);
        ImageButton imageButtonQuestion_weight = findViewById(R.id.imageButtonQuestion_weight);

        LineChart lineChart_weight = findViewById(R.id.lineChart_weight);
        LinearLayout linearLayoutChart_weight = findViewById(R.id.linearLayoutChart_weight);
        LinearLayout linearLayoutChartInfo_weight = findViewById(R.id.linearLayoutChartInfo_weight);
        LinearLayout linearLayoutProperWeight_weight = findViewById(R.id.linearLayoutProperWeight_weight);
        scrollView_weight = findViewById(R.id.scrollView_weight);

        SharedPreferences prefsChartInfo = getSharedPreferences("prefsChartInfo", MODE_PRIVATE);
        SharedPreferences prefsWeightInfo = getSharedPreferences("prefsWeightInfo", MODE_PRIVATE);

        if (prefsChartInfo.getBoolean("prefsChartInfo", true)) {
            linearLayoutChart_weight.setVisibility(View.VISIBLE);
        } else {
            linearLayoutChart_weight.setVisibility(View.GONE);
            linearLayoutChartInfo_weight.setVisibility(View.GONE);
            System.out.println("DS");
        }

        if (prefsWeightInfo.getBoolean("prefsWeightInfo", true))
            linearLayoutProperWeight_weight.setVisibility(View.VISIBLE);
        else
            linearLayoutProperWeight_weight.setVisibility(View.GONE);



        if (FlagSetup.getFlagWeightAdd() == 0) {
            buttonAdd_weight.setVisibility(View.GONE);
            buttonSaveEdit_weight.setVisibility(View.VISIBLE);
            String weightKey = getIntent().getStringExtra("weightKey");
            String dateKey = getIntent().getStringExtra("dateKey");
            imageButtonDate_weight.setVisibility(View.GONE);

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
            if (prefsChartInfo.getBoolean("prefsChartInfo", true))
                linearLayoutChartInfo_weight.setVisibility(View.VISIBLE);
            lineChart_weight.setVisibility(View.GONE);
            linearLayoutChart_weight.setVisibility(View.GONE);
        }


        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOWeight daoWeight = db.daoWeight();




        SharedPreferences prefsGetRodentId = getSharedPreferences("prefsGetRodentId", MODE_PRIVATE);
        List<RodentWithWeights> theLastWeight = daoWeight.getLastWeightByRodentId(prefsGetRodentId.getInt("rodentId", 0));


        try {

            Date birth = java.sql.Date.valueOf( theLastWeight.get(0).rodents.get(0).getBirth().toString());

            List<Integer> listAge = new ArrayList<>();
            listAge = AgeCalculator.calculateAge(birth);
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



        imageButtonDate_weight.setOnClickListener(view -> onDateClick());

        imageButtonQuestion_weight.setOnClickListener(view -> {
            SharedPreferences prefsFirstStart = getApplicationContext().getSharedPreferences("prefsFirstStart", Context.MODE_PRIVATE);

            WeightPetHealthInfo weightPetHealthInfo = new WeightPetHealthInfo();
            if (prefsFirstStart.getInt("prefsFirstStart", 0) == 1)
                weightPetHealthInfo.tableInfoChinchilla(WeightView.this); // do zmiany
            else if (prefsFirstStart.getInt("prefsFirstStart", 0) == 2)
                weightPetHealthInfo.tableInfoChinchilla(WeightView.this); //do zmiany
            else if (prefsFirstStart.getInt("prefsFirstStart", 0) == 3)
                weightPetHealthInfo.tableInfoChinchilla(WeightView.this);

            finish();
        });



        dateSetListener = (datePicker, year, month, day) -> {
            month += 1;

            String date = (year + "-" + month + "-" + day);
            textViewDateWeight_hidden.setText(date);
            textViewDate.setText(DateFormat.formatDate(java.sql.Date.valueOf(date)));
        };


        buttonAdd_weight.setOnClickListener(view -> saveWeight());

        buttonSaveEdit_weight.setOnClickListener(view -> saveEditWeight());


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
                java.sql.Date.valueOf(textViewDateWeight_hidden.getText().toString()))) {
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

    private void reloadActivity() {
        startActivity(new Intent(WeightView.this, WeightView.class));
        finish();
    }


}
