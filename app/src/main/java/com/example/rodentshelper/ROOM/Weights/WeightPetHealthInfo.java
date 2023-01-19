package com.example.rodentshelper.ROOM.Weights;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class WeightPetHealthInfo {

    public void getPreference(Context context, Integer ageYear, Integer ageMonth, Integer ageDay, Integer weight, TextView textViewInfo_weight) {
        SharedPreferences prefsFirstStart = context.getSharedPreferences("prefsFirstStart", MODE_PRIVATE);


        if (prefsFirstStart.getInt("prefsFirstStart", 0) == 3) {
            petHealth3(ageYear, ageMonth, ageDay, weight, textViewInfo_weight);
        }
    }

    private void petHealth3(Integer ageYear, Integer ageMonth, Integer ageDay, Integer weight, TextView textViewInfo_weight) {
        //30g - 100g
        if (ageYear == 0 && ageMonth == 0 && ageDay < 14) {
            if (weight >= 30 && weight <= 100)
                properWeight(textViewInfo_weight, 30, 100);
            if (weight < 30)
                tooLowWeight(textViewInfo_weight, 30, 100);
            if (weight > 100)
                tooHighWeight(textViewInfo_weight, 30, 100);
            return;
        }


        //100g - 250g
        if (ageYear == 0 && ageMonth <= 2 ) {

            //70g - 150g
            if (ageMonth == 0 && ageDay >= 14) {
                if (weight >= 70 && weight <= 150)
                    properWeight(textViewInfo_weight, 70, 150);
                if (weight < 70)
                    tooLowWeight(textViewInfo_weight, 70, 150);
                if (weight > 150)
                    tooHighWeight(textViewInfo_weight, 70, 150);
                return;
            }

            if (weight >= 100 && weight <= 250)
                properWeight(textViewInfo_weight, 100, 250);
            if (weight < 100)
                tooLowWeight(textViewInfo_weight, 100, 250);
            if (weight > 250)
                tooHighWeight(textViewInfo_weight, 100, 250);
            return;
        }

        //200g - 350g
        if (ageYear == 0 && (ageMonth >= 2 && ageMonth <= 4)) {
            if (weight >= 200 && weight <= 350)
                properWeight(textViewInfo_weight, 200, 350);
            if (weight < 200)
                tooLowWeight(textViewInfo_weight, 200, 350);
            if (weight > 350)
                tooHighWeight(textViewInfo_weight, 200, 350);
            return;
        }

        //300g - 480g
        if (ageYear == 0 && (ageMonth >= 4 && ageMonth <= 7)) {
            if (weight >= 300 && weight <= 480)
                properWeight(textViewInfo_weight, 300, 480);
            if (weight < 300)
                tooLowWeight(textViewInfo_weight, 380, 560);
            if (weight > 480)
                tooHighWeight(textViewInfo_weight, 380, 560);
            return;
        }

        //380g - 560g
        if (ageYear == 0 && (ageMonth >= 7 && ageMonth < 12)) {
            if (weight >= 380 && weight <= 560)
                properWeight(textViewInfo_weight, 380, 560);
            if (weight < 380)
                tooLowWeight(textViewInfo_weight, 380, 560);
            if (weight > 560)
                tooHighWeight(textViewInfo_weight, 380, 560);
            return;
        }

        //410g - 780g
        if (ageYear >= 1) {
            if (weight >= 410 && weight <= 780)
                properWeight(textViewInfo_weight, 410, 780);
            if (weight < 410)
                tooLowWeight(textViewInfo_weight, 410, 780);
            if (weight > 780)
                tooHighWeight(textViewInfo_weight, 410, 780);
        }

    }

    private void properWeight (TextView textViewInfo_weight, Integer weight1, Integer weight2) {

        SpannableString bold = boldText("Twój pupil jest w bardzo dobrej kondycji wagowej.");
        textViewInfo_weight.setText(bold);

        textViewInfo_weight.append("\nPrawidłowy przedział wagowy dla twojego zwierzęcia w aktualnym wieku wynosi:\n" +
                weight1 + "g - " + weight2 +"g.");
    }

    private void tooLowWeight (TextView textViewInfo_weight, Integer weight1, Integer weight2) {
        SpannableString bold = boldText("Twój pupil ma zbyt niską wagę.");
        textViewInfo_weight.setText(bold);

        textViewInfo_weight.append("\nPrawidłowy przedział wagowy dla twojego zwierzęcia w aktualnym wieku wynosi:\n" +
                weight1 + "g - " + weight2 +"g.");
    }

    private void tooHighWeight (TextView textViewInfo_weight, Integer weight1, Integer weight2) {
        SpannableString bold = boldText("Twój pupil ma zbyt dużą wagę.");
        textViewInfo_weight.setText(bold);

        textViewInfo_weight.append("\nPrawidłowy przedział wagowy dla twojego zwierzęcia w aktualnym wieku wynosi:\n" +
                weight1 + "g - " + weight2 +"g.");
    }


    public void tableInfoChinchilla (Context context) {
        context.startActivity(new Intent(context, WeightInfoChinchilla.class));
    }


    private SpannableString boldText(String boldText) {
        SpannableString str = new SpannableString(boldText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }
}
