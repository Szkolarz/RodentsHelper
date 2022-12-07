package com.example.rodentshelper.ROOM.Weights;

import android.annotation.SuppressLint;
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
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAOWeight;
import com.example.rodentshelper.ROOM.DateFormat;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.ROOM._MTM._RodentWeight.RodentWithWeights;
import com.github.mikephil.charting.charts.LineChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class WeightInfo extends Activity {

    TextView textView_weightInfo;


    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight_info);

        textView_weightInfo = findViewById(R.id.textView_weightInfo);

        textView_weightInfo.setText("Pamiętaj, że przedstawiona powyżej rozpiska prawidłowej wagi pupila jest kwestią umowną i zależy często od genów, czy od hodowli, z której pochodzi zwierzę.\n" +
                "Dlatego ewentualne, minimalne odbieganie wagi twojego zwierzęcia od normy nie powinno cię niepokoić.\n\n" +
                "Jeśli zauważysz jednak coś niepokojącego w wadze twojego zwierzęcia, udaj się koniecznie do weterynarza.");


    }

}
