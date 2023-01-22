package com.android.rodentshelper.Encyclopedia.Treats;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.rodentshelper.Encyclopedia.Common.EncyclopediaTab;

public class PagerAdapterTreats extends FragmentStateAdapter {


    public PagerAdapterTreats(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new EncyclopediaTab();
    }


    @Override
    public int getItemCount() {
        return 2;
    }





}
