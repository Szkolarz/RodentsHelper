package com.android.rodentshelper.ActivitiesFromNavbar;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.android.rodentshelper.ROOM.Rodent.ViewRodents;

public class ActivityRodents implements View.OnClickListener{


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ViewRodents.class);

        view.getContext().startActivity(intent);
        ((Activity)view.getContext()).finishAffinity();
    }
}
