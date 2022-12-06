package com.example.rodentshelper.Encyclopedia.Treats;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAORodents;
import com.example.rodentshelper.ROOM.Rodent.RodentModel;

import java.util.List;

public class PagerAdapterTreats extends FragmentStateAdapter {


    private Integer noOfTabs;

    public PagerAdapterTreats(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public PagerAdapterTreats(@NonNull Fragment fragment) {
        super(fragment);
    }

    public PagerAdapterTreats(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {


        return new TreatsTab1(ViewTreats.getData()[position]);

    }

    @Override
    public int getItemCount() {
        return 2;
    }





}
