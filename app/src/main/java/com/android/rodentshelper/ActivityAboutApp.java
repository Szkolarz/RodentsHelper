package com.android.rodentshelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.android.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.android.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.android.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.android.rodentshelper.DatabaseManagement.ActivityLogin;
import com.android.rodentshelper.ROOM.Rodent.ViewRodents;
import com.android.rodentshelper.SQL.Querries;
import com.example.rodentshelper.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class ActivityAboutApp extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("O aplikacji");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);


        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(ActivityAboutApp.this, ViewRodents.class);
            startActivity(intent);
            finish();
        });

        ImageView imageViewPANS = findViewById(R.id.imageViewPANS);
        TextView textViewFlaticon = findViewById(R.id.textViewFlaticon);
        TextView textViewFreepik = findViewById(R.id.textViewFreepik);
        TextView textViewStowarzyszenie = findViewById(R.id.textViewStowarzyszenie);


        imageViewPANS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://pans.nysa.pl/";
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });


        String linkStowarzyszenie = "<a href=\"https://stowarzyszenie.forum-szynszyla.pl/\">Stowarzyszenie Miłośników Szynszyli Małej</a>";
        String linkFlaticon ="<a href=\"https://www.flaticon.com/\">flaticon.com</a>";
        String linkFreepik ="<a href=\"https://www.flaticon.com/authors/freepik/\">Freepik</a>";


        textViewFlaticon.append(Html.fromHtml(linkFlaticon, 0));

        textViewStowarzyszenie.append(Html.fromHtml(linkStowarzyszenie, 0));
        textViewStowarzyszenie.setMovementMethod(LinkMovementMethod.getInstance());

        textViewFreepik.append(Html.fromHtml(linkFreepik, 0));
        textViewFreepik.setMovementMethod(LinkMovementMethod.getInstance());

        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());
    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(ActivityAboutApp.this, ViewRodents.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}