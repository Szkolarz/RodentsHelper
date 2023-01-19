package com.example.rodentshelper.Encyclopedia.Treats;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.rodentshelper.Encyclopedia.Common.EncyclopediaTab;
import com.example.rodentshelper.Encyclopedia.Common.ViewCagesupplyAndTreats;

public class PagerAdapterTreats extends FragmentStateAdapter {


    public PagerAdapterTreats(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new EncyclopediaTab(ViewCagesupplyAndTreats.getData()[position]);
    }


    @Override
    public int getItemCount() {
        return 2;
    }





}
