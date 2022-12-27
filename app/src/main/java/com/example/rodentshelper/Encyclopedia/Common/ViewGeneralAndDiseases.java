package com.example.rodentshelper.Encyclopedia.Common;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.example.rodentshelper.Encyclopedia.General.AdapterGeneral;
import com.example.rodentshelper.Encyclopedia.General.GeneralModel;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAOEncyclopedia;

import java.util.List;

public class ViewGeneralAndDiseases extends AppCompatActivity  {


    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_with_photo);

        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;
        TextView textView2_encyclopedia;
        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);

        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());


        textView2_encyclopedia = findViewById(R.id.textView2_encyclopedia);
        imageButton2_encyclopedia.setColorFilter(Color.WHITE);
        textView2_encyclopedia.setTextColor(Color.WHITE);




        ImageView imageView_general = findViewById(R.id.imageView_general);
        TextView textViewName_general = findViewById(R.id.textViewName_general);
        TextView textViewDesc_general = findViewById(R.id.textViewDesc_general);

        LinearLayout linearLayout_encyclopedia = findViewById(R.id.linearLayout_encyclopedia);

        InsertRecords insertRecords = new InsertRecords();

        List<GeneralModel> generalModel = insertRecords.getListOfRecords(getApplicationContext());

        textViewName_general.setText("SZYNSZYLA");
        textViewDesc_general.setText(generalModel.get(0).getDescription());

        Bitmap bitmap = BitmapFactory.decodeByteArray(generalModel.get(0).getImage(),
                0, generalModel.get(0).getImage().length);
        imageView_general.setImageBitmap(bitmap);

        getRoomData(generalModel);

    }


    public List getListGeneral(){

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "rodents_helper").allowMainThreadQueries().build();
        DAOEncyclopedia daoEncyclopedia = db.daoEncyclopedia();


        SharedPreferences prefsFirstStart = getSharedPreferences("prefsFirstStart", MODE_PRIVATE);

        List<GeneralModel> generalModel = daoEncyclopedia.getAllGeneral(prefsFirstStart.getInt("prefsFirstStart", 0));


        db.close();
        return generalModel;
    }


    public void getRoomData(List<GeneralModel> generalModel)
    {


        recyclerView = findViewById(R.id.recyclerView_general);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewGeneralAndDiseases.this));

        AdapterGeneral adapter = new AdapterGeneral(getListGeneral());

        recyclerView.setAdapter(adapter);
    }




}