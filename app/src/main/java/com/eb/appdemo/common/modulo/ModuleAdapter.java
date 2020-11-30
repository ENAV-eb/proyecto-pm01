package com.eb.appdemo.common.modulo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.eb.appdemo.R;
import com.eb.appdemo.common.new2.GoogleMapsFragment;
import com.eb.appdemo.common.util.Constantes;

public class ModuleAdapter extends FragmentStateAdapter {

    int totalTabs;
    NewModuloFragment moduloFragment;

    public ModuleAdapter(@NonNull FragmentActivity fragmentActivity, int totalTabs, NewModuloFragment moduloFragment) {
        super(fragmentActivity);
        this.totalTabs = totalTabs;
        this.moduloFragment = moduloFragment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:

                NewModuleChildPleaFragment pleaTab = new NewModuleChildPleaFragment(moduloFragment);
                return  pleaTab;
            case 1:

                NewModuleChildTimeFragment timeTab = new NewModuleChildTimeFragment(moduloFragment);
                return timeTab;
            case 2:

                TestCredentialsFragment credTab = new TestCredentialsFragment(moduloFragment);
                return credTab;
            case 3:

                GoogleMapsFragment mapTab = new GoogleMapsFragment(moduloFragment);
                return mapTab;
            case 4:

                DetalleRegistroModuloFragment detalleTab = new DetalleRegistroModuloFragment(moduloFragment);
                return detalleTab;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return totalTabs;
    }
}
