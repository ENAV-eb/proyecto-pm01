package com.eb.appdemo.common.modulo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ModuleAdapter extends FragmentStateAdapter {

    int totalTabs;

    public ModuleAdapter(@NonNull FragmentActivity fragmentActivity, int totalTabs) {
        super(fragmentActivity);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                NewModuleChildPleaFragment pleaTab = new NewModuleChildPleaFragment();
                return  pleaTab;
            case 1:
                NewModuleChildTimeFragment timeTab = new NewModuleChildTimeFragment();
                return timeTab;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return totalTabs;
    }
}
