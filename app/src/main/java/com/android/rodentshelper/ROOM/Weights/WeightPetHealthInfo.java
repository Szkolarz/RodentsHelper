package com.android.rodentshelper.ROOM.Weights;

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


        if (prefsFirstStart.getInt("prefsFirstStart", 0) == 1) {
            petHealth1(ageYear, ageMonth, ageDay, weight, textViewInfo_weight);
        }
        if (prefsFirstStart.getInt("prefsFirstStart", 0) == 2) {
            petHealth2(ageYear, ageMonth, ageDay, weight, textViewInfo_weight);
        }
        if (prefsFirstStart.getInt("prefsFirstStart", 0) == 3) {
            petHealth3(ageYear, ageMonth, ageDay, weight, textViewInfo_weight);
        }
    }

    private void petHealth1(Integer ageYear, Integer ageMonth, Integer ageDay, Integer weight, TextView textViewInfo_weight) {
        //60 g - 160 g
        if (ageYear == 0 && ageMonth == 0 && ageDay < 14) {
            if (weight >= 60 && weight <= 160)
                properWeight(textViewInfo_weight, 60, 160);
            if (weight < 60)
                tooLowWeight(textViewInfo_weight, 60, 160);
            if (weight > 160)
                tooHighWeight(textViewInfo_weight, 60, 160);
            return;
        }


        //200 g - 500 g
        if (ageYear == 0 && ageMonth < 2 ) {

            //100 g - 310 g
            if (ageMonth == 0 && ageDay >= 14) {
                if (weight >= 100 && weight <= 310)
                    properWeight(textViewInfo_weight, 100, 310);
                if (weight < 100)
                    tooLowWeight(textViewInfo_weight, 100, 310);
                if (weight > 310)
                    tooHighWeight(textViewInfo_weight, 100, 310);
                return;
            }

            if (weight >= 200 && weight <= 500)
                properWeight(textViewInfo_weight, 200, 500);
            if (weight < 200)
                tooLowWeight(textViewInfo_weight, 200, 500);
            if (weight > 500)
                tooHighWeight(textViewInfo_weight, 200, 500);
            return;
        }

        //340 g - 830 g
        if (ageYear == 0 && (ageMonth >= 2 && ageMonth < 4)) {
            if (weight >= 340 && weight <= 830)
                properWeight(textViewInfo_weight, 340, 830);
            if (weight < 340)
                tooLowWeight(textViewInfo_weight, 340, 830);
            if (weight > 830)
                tooHighWeight(textViewInfo_weight, 340, 830);
            return;
        }

        //550 g - 980 g
        if (ageYear == 0 && (ageMonth >= 4 && ageMonth < 7)) {
            if (weight >= 550 && weight <= 980)
                properWeight(textViewInfo_weight, 550, 980);
            if (weight < 550)
                tooLowWeight(textViewInfo_weight, 550, 980);
            if (weight > 980)
                tooHighWeight(textViewInfo_weight, 550, 980);
            return;
        }



        //690 g - 1180 g
        if (ageYear == 0 && (ageMonth >= 7 && ageMonth < 12)) {
            if (weight >= 690 && weight <= 1180)
                properWeight(textViewInfo_weight, 690, 1180);
            if (weight < 690)
                tooLowWeight(textViewInfo_weight, 690, 1180);
            if (weight > 1180)
                tooHighWeight(textViewInfo_weight, 690, 1180);
            return;
        }

        //730 g - 1320 g
        if (ageYear >= 1) {
            if (weight >= 730 && weight <= 670)
                properWeight(textViewInfo_weight, 730, 1320);
            if (weight < 730)
                tooLowWeight(textViewInfo_weight, 730, 1320);
            if (weight > 1320)
                tooHighWeight(textViewInfo_weight, 730, 1320);
        }

    }
    private void petHealth2(Integer ageYear, Integer ageMonth, Integer ageDay, Integer weight, TextView textViewInfo_weight) {
        //6 g - 55 g
        if (ageYear == 0 && ageMonth == 0 && ageDay < 14) {
            if (weight >= 6 && weight <= 55)
                properWeight(textViewInfo_weight, 6, 55);
            if (weight < 6)
                tooLowWeight(textViewInfo_weight, 6, 55);
            if (weight > 55)
                tooHighWeight(textViewInfo_weight, 6, 55);
            return;
        }


        //80 g - 190 g
        if (ageYear == 0 && ageMonth < 2 ) {

            //25 g - 95 g
            if (ageMonth == 0 && ageDay >= 14) {
                if (weight >= 25 && weight <= 95)
                    properWeight(textViewInfo_weight, 25, 95);
                if (weight < 25)
                    tooLowWeight(textViewInfo_weight, 25, 95);
                if (weight > 95)
                    tooHighWeight(textViewInfo_weight, 25, 95);
                return;
            }

            if (weight >= 80 && weight <= 190)
                properWeight(textViewInfo_weight, 80, 190);
            if (weight < 80)
                tooLowWeight(textViewInfo_weight, 80, 190);
            if (weight > 190)
                tooHighWeight(textViewInfo_weight, 80, 190);
            return;
        }

        //160 g - 390 g
        if (ageYear == 0 && (ageMonth >= 2 && ageMonth < 6)) {
            if (weight >= 160 && weight <= 390)
                properWeight(textViewInfo_weight, 160, 390);
            if (weight < 160)
                tooLowWeight(textViewInfo_weight, 160, 390);
            if (weight > 390)
                tooHighWeight(textViewInfo_weight, 160, 390);
            return;
        }



        //260 g - 610 g
        if (ageYear == 0 && (ageMonth >= 6 && ageMonth < 12)) {
            if (weight >= 260 && weight <= 610)
                properWeight(textViewInfo_weight, 260, 610);
            if (weight < 260)
                tooLowWeight(textViewInfo_weight, 260, 610);
            if (weight > 610)
                tooHighWeight(textViewInfo_weight, 260, 610);
            return;
        }

        //330 g - 670 g
        if (ageYear >= 1) {
            if (weight >= 330 && weight <= 670)
                properWeight(textViewInfo_weight, 330, 670);
            if (weight < 330)
                tooLowWeight(textViewInfo_weight, 330, 670);
            if (weight > 670)
                tooHighWeight(textViewInfo_weight, 330, 670);
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
        if (ageYear == 0 && ageMonth < 2) {

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
        if (ageYear == 0 && (ageMonth >= 2 && ageMonth < 4)) {
            if (weight >= 200 && weight <= 350)
                properWeight(textViewInfo_weight, 200, 350);
            if (weight < 200)
                tooLowWeight(textViewInfo_weight, 200, 350);
            if (weight > 350)
                tooHighWeight(textViewInfo_weight, 200, 350);
            return;
        }

        //300g - 480g
        if (ageYear == 0 && (ageMonth >= 4 && ageMonth < 7)) {
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

        //420g - 800 g
        if (ageYear >= 1) {
            if (weight >= 420 && weight <= 800)
                properWeight(textViewInfo_weight, 420, 800);
            if (weight < 420)
                tooLowWeight(textViewInfo_weight, 420, 800);
            if (weight > 800)
                tooHighWeight(textViewInfo_weight, 420, 800);
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
        context.startActivity(new Intent(context, WeightInfoTable.class));
    }


    private SpannableString boldText(String boldText) {
        SpannableString str = new SpannableString(boldText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }
}
