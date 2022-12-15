package com.example.rodentshelper.ActivitiesFromNavbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.rodentshelper.MainViews.ViewEncyclopedia;
import com.example.rodentshelper.MainViews.ViewHealth;
import com.example.rodentshelper.MainViews.ViewOther;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;

public class ActivityEncyclopedia implements View.OnClickListener{




    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ViewEncyclopedia.class);

        view.getContext().startActivity(intent);
        ((Activity)view.getContext()).finishAffinity();

    }
}
