package com.example.rodentshelper.ROOM.Weights;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;


public class WeightInfoChinchilla extends Activity {

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight_info_chinchilla);

        TextView textView_weightInfo = findViewById(R.id.textView_weightInfo);

        textView_weightInfo.setText("Pamiętaj, że przedstawiona powyżej rozpiska prawidłowej wagi pupila jest kwestią umowną i zależy często od genów, czy od hodowli, z której pochodzi zwierzę.\n" +
                "Dlatego ewentualne, minimalne odbieganie wagi twojego zwierzęcia od normy nie powinno cię niepokoić.\n\n" +
                "Jeśli zauważysz jednak coś niepokojącego w wadze twojego zwierzęcia, udaj się koniecznie do weterynarza.");

        FlagSetup.setFlagWeightAdd(1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(WeightInfoChinchilla.this, WeightView.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
