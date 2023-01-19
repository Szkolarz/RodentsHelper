package com.example.rodentshelper.ActivitiesFromNavbar;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.rodentshelper.MainViews.ViewOther;

public class ActivityOther implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ViewOther.class);

        view.getContext().startActivity(intent);
        ((Activity)view.getContext()).finishAffinity();
    }
}
