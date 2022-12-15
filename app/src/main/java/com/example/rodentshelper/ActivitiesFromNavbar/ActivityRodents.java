package com.example.rodentshelper.ActivitiesFromNavbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.rodentshelper.ROOM.Rodent.ViewRodents;

public class ActivityRodents implements View.OnClickListener{


    public void closeEncyclopedia(Context context) {
        context.startActivity(new Intent(context, ViewRodents.class));
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ViewRodents.class);

        view.getContext().startActivity(intent);
        ((Activity)view.getContext()).finishAffinity();
    }
}
