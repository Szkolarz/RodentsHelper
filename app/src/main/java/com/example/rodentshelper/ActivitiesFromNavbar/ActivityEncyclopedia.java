package com.example.rodentshelper.ActivitiesFromNavbar;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.rodentshelper.MainViews.ViewEncyclopedia;

public class ActivityEncyclopedia implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ViewEncyclopedia.class);

        view.getContext().startActivity(intent);
        ((Activity)view.getContext()).finishAffinity();

    }
}
