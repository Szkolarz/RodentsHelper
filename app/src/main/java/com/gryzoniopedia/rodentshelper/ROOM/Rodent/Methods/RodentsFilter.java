package com.gryzoniopedia.rodentshelper.ROOM.Rodent.Methods;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.widget.CompoundButtonCompat;

import com.example.rodentshelper.R;
import com.gryzoniopedia.rodentshelper.ROOM.Rodent.ViewRodents;

public class RodentsFilter extends ViewRodents  {


    public void setUpFilter (Activity activity, View inflateView) {



        RadioGroup radioGroupFilterRodents = inflateView.findViewById(R.id.radioGroupFilterRodents);
        RadioButton radioButtonColor, radioButtonDateDesc_rodents, radioButtonDateAsc_rodents,
                radioButtonNameDesc_rodents, radioButtonNameAsc_rodents;


        radioButtonDateDesc_rodents = inflateView.findViewById(R.id.radioButtonDateAsc_rodents);
        radioButtonDateAsc_rodents = inflateView.findViewById(R.id.radioButtonDateDesc_rodents);
        radioButtonNameDesc_rodents = inflateView.findViewById(R.id.radioButtonNameAsc_rodents);
        radioButtonNameAsc_rodents = inflateView.findViewById(R.id.radioButtonNameDesc_rodents);


        SharedPreferences spRodentsRadioOrder = activity.getSharedPreferences("spRodentsRadioOrder", MODE_PRIVATE);


        if (spRodentsRadioOrder.getInt("spRodentsRadioOrder", 1) == 1)
            radioButtonDateDesc_rodents.setChecked(true);
        if (spRodentsRadioOrder.getInt("spRodentsRadioOrder", 1) == 2)
            radioButtonDateAsc_rodents.setChecked(true);
        if (spRodentsRadioOrder.getInt("spRodentsRadioOrder", 1) == 3)
            radioButtonNameDesc_rodents.setChecked(true);
        if (spRodentsRadioOrder.getInt("spRodentsRadioOrder", 1) == 4)
            radioButtonNameAsc_rodents.setChecked(true);


        int selectedRadio1 = radioGroupFilterRodents.getCheckedRadioButtonId();
        radioButtonColor = inflateView.findViewById(selectedRadio1);
        radioButtonColor.setTextColor(Color.parseColor("#FFFFFF"));
        CompoundButtonCompat.setButtonTintList(radioButtonColor, ContextCompat.getColorStateList(activity, R.color.white));


        setUpOnClickRadio(activity, radioGroupFilterRodents, inflateView);

        AlertDialog.Builder alert = new AlertDialog.Builder(activity, R.style.AlertDialogStyleUpdate);

        alert.setView(inflateView);

        alert.setCancelable(false);
        alert.setNegativeButton("Anuluj", (dialog, which) -> Toast.makeText(activity, "Anulowano", Toast.LENGTH_SHORT).show());

        alert.setPositiveButton("Zatwierdź", (dialog, which) -> {

            RadioButton radioButton;

            int count = radioGroupFilterRodents.getChildCount();
            for (int i = 0; i < count; i++) {
                View o = radioGroupFilterRodents.getChildAt(i);
                if (o instanceof RadioButton) {
                    radioButton = (RadioButton) o;
                    if (radioButton.isChecked()) {
                        SharedPreferences.Editor spEditorRodentsRadioOrder = spRodentsRadioOrder.edit();
                        spEditorRodentsRadioOrder.putInt("spRodentsRadioOrder", i+1);
                        spEditorRodentsRadioOrder.apply();
                    }
                }
            }


            Intent intent = new Intent(activity, ViewRodents.class);
            activity.startActivity(intent);
            ((Activity)activity).finish();

        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }


    private void setUpOnClickRadio (Context context, RadioGroup radioGroupFilterRodents, View inflateLayout) {

        radioGroupFilterRodents.setOnCheckedChangeListener((group, checkedId) -> {
            changeColors(context, group, radioGroupFilterRodents, inflateLayout);
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
