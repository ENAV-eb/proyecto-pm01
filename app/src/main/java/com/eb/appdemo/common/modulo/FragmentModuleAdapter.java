package com.eb.appdemo.common.modulo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.eb.appdemo.common.new2.ContactPhoneFragment;

public class FragmentModuleAdapter extends FragmentPagerAdapter {

    int totalTabs;
    NewModuloFragment moduloFragment;

    public FragmentModuleAdapter(@NonNull FragmentManager fm, int totalTabs, NewModuloFragment moduloFragment) {
        super(fm);
        this.moduloFragment = moduloFragment;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:

                NewModuleChildPleaFragment pleaTab = new NewModuleChildPleaFragment(moduloFragment);
                return  pleaTab;
            case 1:

                NewModuleChildTimeFragment timeTab = new NewModuleChildTimeFragment(moduloFragment);
                return timeTab;
            case 2:
                //TODO Cmabiar a fragment de agregar responsable de lista de contactos
                ContactPhoneFragment contactPhoneFragment =  new ContactPhoneFragment(moduloFragment);
                return contactPhoneFragment;
                /*
                TestCredentialsFragment credTab = new TestCredentialsFragment(moduloFragment);
                return credTab;

                 */
            case 3:
                DetalleRegistroModuloFragment detalleTab = new DetalleRegistroModuloFragment(moduloFragment);
                return detalleTab;
                /*
                GoogleMapsFragment mapTab = new GoogleMapsFragment(moduloFragment);
                return mapTab;


            case 4:

                DetalleRegistroModuloFragment detalleTab = new DetalleRegistroModuloFragment(moduloFragment);
                return detalleTab;

                 */
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
