package com.example.rodentshelper.ROOM.Weights;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;


public class WeightInfoTable extends Activity {

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefsFirstStart = getApplicationContext().getSharedPreferences("prefsFirstStart", Context.MODE_PRIVATE);

        if (prefsFirstStart.getInt("prefsFirstStart", 0) == 1)
            setContentView(R.layout.weight_info_guineapig);
        if (prefsFirstStart.getInt("prefsFirstStart", 0) == 2)
            setContentView(R.layout.weight_info_rat);
        if (prefsFirstStart.getInt("prefsFirstStart", 0) == 3)
            setContentView(R.layout.weight_info_chinchilla);


        TextView textView_weightInfo = findViewById(R.id.textView_weightInfo);

        textView_weightInfo.setText("Pamiętaj, że przedstawiona powyżej tabela prawidłowej wagi jest kwestią umowną i zależy często od genów, czy od hodowli, z której pochodzi zwierzę.\n" +
                "Dlatego ewentualne, minimalne odbieganie wagi twojego zwierzęcia od normy nie powinno cię niepokoić.\n\n" +
                "Każde zwierzę rozwija się indywidualnie, a powyższa tabela jest tylko elementem informacyjnym. Aby upewnić się o prawidłowym stanie zdrowia zwierzęcia, udaj się koniecznie do weterynarza.");

        FlagSetup.setFlagWeightAdd(1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(WeightInfoTable.this, WeightView.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
