package com.android.rodentshelper.MainViews;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.android.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.android.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.android.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.android.rodentshelper.FlagSetup;
import com.example.rodentshelper.R;
import com.android.rodentshelper.ROOM.Medicaments.ViewMedicaments;
import com.android.rodentshelper.ROOM.Notes.ViewNotes;
import com.android.rodentshelper.ROOM.Rodent.ViewRodents;
import com.android.rodentshelper.ROOM.Vet.ViewVets;
import com.android.rodentshelper.ROOM.Visits.ViewVisits;
import com.android.rodentshelper.ROOM.Weights.WeightView;

import java.util.Objects;

public class ViewPetHealth extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_health);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Opieka nad pupilem");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(ViewPetHealth.this, ViewRodents.class);
            startActivity(intent);
            finish();
        });

        ImageView imageButton1_rodent, imageButton2_encyclopedia, imageButton3_health, imageButton4_other;

        imageButton1_rodent = findViewById(R.id.imageButton1_rodent);
        imageButton2_encyclopedia = findViewById(R.id.imageButton2_encyclopedia);
        imageButton3_health = findViewById(R.id.imageButton3_health);
        imageButton4_other = findViewById(R.id.imageButton4_other);

        imageButton1_rodent.setOnClickListener(new ActivityRodents());
        imageButton2_encyclopedia.setOnClickListener(new ActivityEncyclopedia());
        imageButton3_health.setOnClickListener(new ActivityHealth());
        imageButton4_other.setOnClickListener(new ActivityOther());

        Integer id_animalKey = Integer.parseInt(getIntent().getStringExtra("id_animalKey"));
        String nameKey = getIntent().getStringExtra("nameKey");

        //setting up flags for making proper relations (for ex. checkbox visibility: gone)
        FlagSetup.setFlagIsFromHealth(false);
        FlagSetup.setFlagVetAdd(2);
        FlagSetup.setFlagVisitAdd(2);
        FlagSetup.setFlagMedAdd(2);


        ImageView imageButtonVet1, imageButtonMed, imageButtonVisit, imageButtonPetHealth_notes, imageButtonPetHealth_weight;
        TextView textView1_rodent, textView1_petHealth, textView2_petHealth, textView3_petHealth, textView4_petHealth, textView5_petHealth;

        imageButtonVet1 = findViewById(R.id.imageButtonVet1);
        imageButtonMed = findViewById(R.id.imageButtonMed);
        imageButtonVisit = findViewById(R.id.imageButtonVisit);
        imageButtonPetHealth_notes = findViewById(R.id.imageButtonPetHealth_notes);
        imageButtonPetHealth_weight = findViewById(R.id.imageButtonPetHealth_weight);

        textView1_petHealth = findViewById(R.id.textView1_petHealth);
        textView2_petHealth = findViewById(R.id.textView2_petHealth);
        textView3_petHealth = findViewById(R.id.textView3_petHealth);
        textView4_petHealth = findViewById(R.id.textView4_petHealth);
        textView5_petHealth = findViewById(R.id.textView5_petHealth);

        TextView textViewPetName_petHealth = findViewById(R.id.textViewPetName_petHealth);
        TextView textViewInfoPetName_petHealth = findViewById(R.id.textViewInfoPetName_petHealth);
        ImageButton imageButtonQuestion_petHealth = findViewById(R.id.imageButtonQuestion_petHealth);

        SpannableString boldName = boldText(nameKey);


        textViewPetName_petHealth.append(boldName);
        textViewPetName_petHealth.append(".");

        textViewInfoPetName_petHealth.setText("Pod poniższymi przyciskami wyświetlają się tylko elementy, " +
                "do których przypisane jest Twoje zwierzę.\nPrzykładowo: jeśli z poziomu poniższych opcji zostanie " +
                "dodany np. nowy weterynarz - będzie on automatycznie przypisany aktualnie wybranemu pupilowi (");
        textViewInfoPetName_petHealth.append(boldName);
        textViewInfoPetName_petHealth.append(") i nie będzie widoczny z poziomu zielonego przycisku 'Opieka' u innych pupili.");


        textView1_rodent = findViewById(R.id.textView1_rodent);
        imageButton1_rodent.setColorFilter(Color.WHITE);
        textView1_rodent.setTextColor(Color.WHITE);



        imageButtonQuestion_petHealth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showPetHealthInfo(textViewInfoPetName_petHealth, imageButtonQuestion_petHealth);
            }
        });



        imageButtonVet1.setOnClickListener(view -> viewVets());

        imageButtonMed.setOnClickListener(view -> viewMeds());

        imageButtonVisit.setOnClickListener(view -> viewVisits());

        imageButtonPetHealth_notes.setOnClickListener(view -> viewNotes());

        imageButtonPetHealth_weight.setOnClickListener(view -> viewWeight());
    }

    private Boolean imageButtonQuestionFlag = true;

    private SpannableString boldText(String boldText) {
        SpannableString str = new SpannableString(boldText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }

    private void showPetHealthInfo(TextView textViewInfoPetName_petHealth, ImageButton imageButtonQuestion_petHealth) {
        if (imageButtonQuestionFlag) {
            textViewInfoPetName_petHealth.setVisibility(View.VISIBLE);
            imageButtonQuestion_petHealth.setColorFilter(Color.parseColor("#FFFFFF"));
            imageButtonQuestionFlag = false;
            return;
        }
        if (!imageButtonQuestionFlag) {
            textViewInfoPetName_petHealth.setVisibility(View.GONE);
            imageButtonQuestion_petHealth.setColorFilter(Color.parseColor("#0A4A97"));
            imageButtonQuestionFlag = true;
        }

    }

    public void viewVets()
    {
        Intent intent = new Intent(ViewPetHealth.this, ViewVets.class);
        startActivity(intent);
    }

    public void viewWeight()
    {
        FlagSetup.setFlagWeightAdd(1);
        Intent intent = new Intent(ViewPetHealth.this, WeightView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void viewMeds()
    {
        Intent intent = new Intent(ViewPetHealth.this, ViewMedicaments.class);
        startActivity(intent);
    }

    public void viewVisits()
    {
        Intent intent = new Intent(ViewPetHealth.this, ViewVisits.class);
        startActivity(intent);
    }

    public void viewNotes()
    {
        Intent intent = new Intent(ViewPetHealth.this, ViewNotes.class);


        startActivity(intent);
    }
}