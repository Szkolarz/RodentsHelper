package com.gryzoniopedia.rodentshelper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.ViewRodents;

import java.util.Objects;

public class ActivityProgressBar extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar);

        changeTextViewContent(getIntent().getStringExtra("content"));

    }

    public void closeActivity () {
        ActivityProgressBar.this.finish();
    }

    public void changeTextViewContent (String content) {
            TextView textViewProgressBar = findViewById(R.id.textViewProgressBar);
            textViewProgressBar.setText(content);
    }


    @Override
    public void onBackPressed() {
        TextView textViewProgressBarBack = findViewById(R.id.textViewProgressBarBack);
        textViewProgressBarBack.setVisibility(View.VISIBLE);
    }



}