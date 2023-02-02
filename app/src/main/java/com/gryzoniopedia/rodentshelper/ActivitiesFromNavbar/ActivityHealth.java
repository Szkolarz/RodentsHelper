package com.gryzoniopedia.rodentshelper.ActivitiesFromNavbar;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.gryzoniopedia.rodentshelper.MainViews.ViewHealth;

public class ActivityHealth implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ViewHealth.class);

        view.getContext().startActivity(intent);
        ((Activity)view.getContext()).finishAffinity();
    }
}