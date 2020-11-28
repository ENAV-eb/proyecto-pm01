package com.eb.appdemo.common.new2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.eb.appdemo.R;
import com.eb.appdemo.common.config.ConfigParentFragment;
import com.eb.appdemo.common.modulo.MainPageFragment;
import com.eb.appdemo.common.modulo.NewModuloFragment;
import com.eb.appdemo.entidades.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class KeeperMainPageActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    BottomNavigationView btnMainNavigation;

    private FirebaseAuth mAuth;
    private MaterialTextView user_welcome;
    private ImageView user_icon;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keeper_main_page);


        hooks();

        mAuth = FirebaseAuth.getInstance();


        asignarReferenciasMenu();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null) {
            try {
                updateHeader(currentUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void hooks(){
        user_welcome = findViewById(R.id.user_welcome);
        user_icon = findViewById(R.id.user_icon);
    }


    private void updateHeader(FirebaseUser user) throws IOException {

        Query userQuery = FirebaseDatabase.getInstance()
                .getReference("users")
                .orderByChild("id")
                .equalTo(user.getUid());

        Log.i(TAG, "Query userQuery: " + userQuery.toString());

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    User user = dataSnapshot.getValue(User.class);

                    Log.i(TAG, "Query user:" + user.toString());
                    user_welcome.setText("Bievenido, " + user.getUserCompleteName());
                    if(!user.getPhotoUrl().trim().isEmpty()) {
                        new DownloadImageTask((ImageView) findViewById(R.id.user_icon))
                                .execute(user.getPhotoUrl());

                    } else {
                        //TODO default user icon set
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

