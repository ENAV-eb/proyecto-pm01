package com.eb.appdemo.common.new2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.eb.appdemo.R;
import com.eb.appdemo.common.config.ConfigParentFragment;
import com.eb.appdemo.common.modulo.MainPageFragment;
import com.eb.appdemo.common.modulo.NewModuloFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class KeeperMainPageActivity extends AppCompatActivity {

    BottomNavigationView btnMainNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keeper_main_page);

        Bundle bundle = getIntent().getExtras();
        String uid = bundle.getString("uid");
        String first_name = bundle.getString("first_name");
        String last_name = bundle.getString("last_name");
        String providerType = bundle.getString("providerType");

        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file), Context.MODE_PRIVATE);

        SharedPreferences.Editor editorSP = sharedPreferences.edit();

        editorSP.putString("user_uid",uid);
        editorSP.putString("user_first_name",first_name);
        editorSP.putString("user_last_name",last_name);
        editorSP.putString("providerType",providerType).toString();
        editorSP.apply();



        asignarReferenciasMenu();
    }

    private void asignarReferenciasMenu(){
        abrirFragmentoMenu(new MainPageFragment());

        btnMainNavigation = findViewById(R.id.btnMainNavigation);
        btnMainNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.menu_home) { abrirFragmentoMenu( new MainPageFragment());}
                if (item.getItemId() == R.id.menu_add_module) { abrirFragmentoMenu( new NewModuloFragment());}
                if (item.getItemId() == R.id.menu_config) { abrirFragmentoMenu( new ConfigParentFragment());}

                return false;
            }
        });
    }

    private void abrirFragmentoMenu(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
}