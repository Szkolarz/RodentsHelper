package com.gryzoniopedia.rodentshelper.ROOM.Notes.Methods;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityEncyclopedia;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityHealth;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityOther;
import com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar.ActivityRodents;
import com.gryzoniopedia.rodentshelper.FlagSetup;
import com.gryzoniopedia.rodentshelper.MainViews.ViewOther;
import com.gryzoniopedia.rodentshelper.ROOM.AppDatabase;
import com.gryzoniopedia.rodentshelper.ROOM.DAONotes;
import com.gryzoniopedia.rodentshelper.ROOM.Notes.ViewNotes;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.ViewRodents;

import java.util.List;
import java.util.Objects;

public class NotesFilter {

    public void setUpFilter (Context context, View alertLayout) {


        RadioGroup radioGroupDate = alertLayout.findViewById(R.id.radioGroupDate);
        RadioGroup radioGroupDisplay = alertLayout.findViewById(R.id.radioGroupDisplay);
        RadioButton radioButton1, radioButton2, radioButtonDate1, radioButtonDate2;
        RadioButton radioButtonAll, radioButton1Month, radioButton3Months,
                radioButton6Months, radioButton1Year;

        radioButtonDate1 = alertLayout.findViewById(R.id.radioButtonDate1);
        radioButtonDate2 = alertLayout.findViewById(R.id.radioButtonDate2);
        radioButtonAll = alertLayout.findViewById(R.id.radioButtonAll);
        radioButton1Month = alertLayout.findViewById(R.id.radioButton1Month);
        radioButton3Months = alertLayout.findViewById(R.id.radioButton3Months);
        radioButton6Months = alertLayout.findViewById(R.id.radioButton6Months);
        radioButton1Year = alertLayout.findViewById(R.id.radioButton1Year);

        SharedPreferences spNotesRadioOrder = context.getSharedPreferences("spNotesRadioOrder", MODE_PRIVATE);
        SharedPreferences spNotesRadioDisplay = context.getSharedPreferences("spNotesRadioDisplay", MODE_PRIVATE);

        if (spNotesRadioOrder.getInt("spNotesRadioOrder", 1) == 1)
            radioButtonDate1.setChecked(true);
        if (spNotesRadioOrder.getInt("spNotesRadioOrder", 1) == 2)
            radioButtonDate2.setChecked(true);

        if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 1)
            radioButtonAll.setChecked(true);
        if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 2)
            radioButton1Month.setChecked(true);
        if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 3)
            radioButton3Months.setChecked(true);
        if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 4)
            radioButton6Months.setChecked(true);
        if (spNotesRadioDisplay.getInt("spNotesRadioDisplay", 1) == 5)
            radioButton1Year.setChecked(true);

        int selectedRadio1 = radioGroupDate.getCheckedRadioButtonId();
        radioButton1 = alertLayout.findViewById(selectedRadio1);
        radioButton1.setTextColor(Color.parseColor("#FFFFFF"));
        CompoundButtonCompat.setButtonTintList(radioButton1, ContextCompat.getColorStateList(context, R.color.white));

        int selectedRadio2 = radioGroupDisplay.getCheckedRadioButtonId();
        radioButton2 = alertLayout.findViewById(selectedRadio2);
        radioButton2.setTextColor(Color.parseColor("#FFFFFF"));
        CompoundButtonCompat.setButtonTintList(radioButton2, ContextCompat.getColorStateList(context, R.color.white));

         /*  */

        setUpOnClickRadio(context, radioGroupDate, radioGroupDisplay, alertLayout);

        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.AlertDialogStyleUpdate);

        alert.setView(alertLayout);

        alert.setCancelable(false);
        alert.setNegativeButton("Anuluj", (dialog, which) -> Toast.makeText(context, "Anulowano", Toast.LENGTH_SHORT).show());

        alert.setPositiveButton("Zatwierdź", (dialog, which) -> {

            RadioButton radioButton;

            int countDate = radioGroupDate.getChildCount();
            for (int i = 0; i < countDate; i++) {
                View o = radioGroupDate.getChildAt(i);
                if (o instanceof RadioButton) {
                    radioButton = (RadioButton) o;
                    if (radioButton.isChecked()) {
                        SharedPreferences.Editor spEitorNotesRadioOrder = spNotesRadioOrder.edit();
                        spEitorNotesRadioOrder.putInt("spNotesRadioOrder", i);
                        spEitorNotesRadioOrder.apply();
                    }
                }
            }

            int countDisplay = radioGroupDisplay.getChildCount();
            for (int i = 0; i < countDisplay; i++) {
                View o = radioGroupDisplay.getChildAt(i);
                if (o instanceof RadioButton) {
                    radioButton = (RadioButton) o;
                    if (radioButton.isChecked()) {
                        SharedPreferences.Editor spEitorNotesRadioDisplay = spNotesRadioDisplay.edit();
                        spEitorNotesRadioDisplay.putInt("spNotesRadioDisplay", i);
                        spEitorNotesRadioDisplay.apply();
                    }
                }
            }


            Intent intent = new Intent(context, ViewNotes.class);
            context.startActivity(intent);
            ((Activity)context).finish();

        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }


    private void setUpOnClickRadio (Context context, RadioGroup radioGroupDate, RadioGroup radioGroupDisplay, View alertLayout) {

        radioGroupDate.setOnCheckedChangeListener((group, checkedId) -> {
            changeColors(context, group, radioGroupDate, alertLayout);
        });

        radioGroupDisplay.setOnCheckedChangeListener((group, checkedId) -> {
            changeColors(context, group, radioGroupDisplay, alertLayout);
        });

    }

    private void changeColors (Context context, RadioGroup group, RadioGroup radioGroupType, View alertLayout) {
        RadioButton radioButton;
        int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            View o = group.getChildAt(i);
            if (o instanceof RadioButton) {
                radioButton = (RadioButton) o;
                radioButton.setTextColor(Color.parseColor("#014D70"));
                CompoundButtonCompat.setButtonTintList(radioButton, ContextCompat.getColorStateList(context, R.color.radioButtonTint));
            }
        }

        int selectedRadio = radioGroupType.getCheckedRadioButtonId();
        radioButton = alertLayout.findViewById(selectedRadio);
        System.out.println(radioButton.getText().toString());
        radioButton.setTextColor(Color.parseColor("#FFFFFF"));
        CompoundButtonCompat.setButtonTintList(radioButton, ContextCompat.getColorStateList(context, R.color.white));
    }

}
