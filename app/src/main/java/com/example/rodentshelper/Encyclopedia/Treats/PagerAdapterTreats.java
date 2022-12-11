package com.example.rodentshelper.Encyclopedia.Treats;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.rodentshelper.Encyclopedia.Common.ViewEncyclopediaData;
import com.example.rodentshelper.Encyclopedia.Common.EncyclopediaTab;

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


        return new EncyclopediaTab(ViewEncyclopediaData.getData()[position]);

    }

    @Override
    public int getItemCount() {
        return 2;
    }





}
