package com.example.rodentshelper.MainViews;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.example.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.example.rodentshelper.FlagSetup;
import com.example.rodentshelper.ROOM.Medicaments.ViewMedicaments;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Notes.ViewNotes;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.ROOM.Vet.ViewVets;
import com.example.rodentshelper.ROOM.Visits.ViewVisits;
import com.example.rodentshelper.ROOM.Weights.WeightView;

public class ViewPetHealth extends AppCompatActivity {

    ImageView imageButtonVet1, imageButtonMed, imageButtonVisit, imageButton1_rodent, imageButtonPetHealth_notes, imageButtonPetHealth_weight;
    TextView textView1_rodent, textView1_petHealth, textView2_petHealth, textView3_petHealth, textView4_petHealth, textView5_petHealth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_health);

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

        SpannableString boldName = boldText(nameKey);


        textView1_petHealth.setText("Na podstawie wagi pupila: ");
        textView1_petHealth.append(boldName);
        textView1_petHealth.append(", otrzymasz informacje o stanie zdrowia zwierzęcia oraz małe porady dotyczące karmienia.");

        textView2_petHealth.setText("Możesz tutaj pisać dowolne notatki na temat twojego pupila. Każda notatka będzie przypisana pupilowi: ");
        textView2_petHealth.append(boldName);
        textView2_petHealth.append(".");

        textView3_petHealth.setText("Pozwala zapisać i przejrzeć weterynarzy twojego pupila: ");
        textView3_petHealth.append(boldName);
        textView3_petHealth.append(". Lista wszystkich weterynarzy jest w zakładce u dołu ekranu: 'Zdrowie'.");

        textView4_petHealth.setText("Pozwala zapisać umówione wizyty z weterynarzem dla pupila: ");
        textView4_petHealth.append(boldName);
        textView4_petHealth.append(". Lista wszystkich wizyt jest u dołu ekrany w zakładce: 'Zdrowie'");

        textView5_petHealth.setText("Umożliwia dodawanie przepisanych leków wraz z ich dawkowaniem pupilowi: ");
        textView5_petHealth.append(boldName);
        textView5_petHealth.append(". Lista wszystkich twoich zapisanych leków znajduje się w zakładce 'Zdrowie' u dołu ekranu.");


        textView1_rodent = findViewById(R.id.textView1_rodent);
        imageButton1_rodent.setColorFilter(Color.WHITE);
        textView1_rodent.setTextColor(Color.WHITE);


        imageButtonVet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewVets();
            }
        });

        imageButtonMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMeds();
            }
        });

        imageButtonVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewVisits();
            }
        });

        imageButtonPetHealth_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewNotes();
            }
        });

        imageButtonPetHealth_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewWeight();
            }
        });






    }

    private SpannableString boldText(String boldText) {
        SpannableString str = new SpannableString(boldText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
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

    public void viewRodents()
    {
        Intent intent = new Intent(ViewPetHealth.this, ViewRodents.class);
        startActivity(intent);
        finish();
    }

    public void viewNotes()
    {
        Intent intent = new Intent(ViewPetHealth.this, ViewNotes.class);


        startActivity(intent);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewRodents();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}