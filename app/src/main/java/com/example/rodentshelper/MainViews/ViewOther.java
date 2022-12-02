package com.example.rodentshelper.MainViews;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rodentshelper.MainViews.GoogleMaps.GoogleMaps;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;

public class ViewOther extends AppCompatActivity {

    ImageView imageButton4_other, imageButtonOther_map;
    TextView textView4_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        imageButton4_other = findViewById(R.id.imageButton4_other);
        textView4_other = findViewById(R.id.textView4_other);
        imageButton4_other.setColorFilter(Color.WHITE);
        textView4_other.setTextColor(Color.WHITE);

        imageButtonOther_map = findViewById(R.id.imageButtonOther_map);

        imageButtonOther_map.setOnClickListener(view -> viewMap());

    }


    private void viewMap() {
        Intent intent = new Intent(ViewOther.this, GoogleMaps.class);
        startActivity(intent);
    }


    public void onClickNavHealth(View view)
    {
        viewHealth();
    }

    public void onClickNavRodent(View view)
    {
        viewRodents();
    }


    public void onClickNavEncyclopedia(View view)
    {
        viewEncyclopedia();
    }

    public void viewRodents() {
        Intent intent = new Intent(ViewOther.this, ViewRodents.class);
        startActivity(intent);
    }

    public void viewHealth() {
        Intent intent = new Intent(ViewOther.this, ViewHealth.class);
        startActivity(intent);
    }

    public void viewEncyclopedia() {
        Intent intent = new Intent(ViewOther.this, ViewEncyclopedia.class);
        startActivity(intent);
    }




    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewRodents();
        }
        return super.onKeyDown(keyCode, event);
    }*/


}