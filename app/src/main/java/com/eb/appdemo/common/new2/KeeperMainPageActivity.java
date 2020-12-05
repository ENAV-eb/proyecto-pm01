package com.eb.appdemo.common.new2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
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
import java.time.LocalDate;
import java.util.List;

public class KeeperMainPageActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    BottomNavigationView btnMainNavigation;

    private FirebaseAuth mAuth;
    private MaterialTextView user_welcome;
    private ImageView user_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keeper_main_page);

        hooks();

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();


        if(currentUser!=null) {
            try {
                updateHeader(currentUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        asignarReferenciasMenu();




    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void hooks(){
        user_welcome = findViewById(R.id.user_welcome);
        user_icon = findViewById(R.id.user_icon);
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

     */


    private void updateHeader(FirebaseUser user) throws IOException {

        Query userQuery = FirebaseDatabase.getInstance()
                .getReference("users")
                .orderByChild("id")
                .equalTo(user.getUid());

        Log.i(TAG, "Query userQuery: " + userQuery.toString());

        //TODO check in can user singleton is not null or empty otherwise execute:
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    User sUser = dataSnapshot.getValue(User.class);

                    User.singletonUser = sUser;

                    Log.i(TAG, "Query user:" + sUser.toString());
                    user_welcome.setText("Bienvenido, " + sUser.getUserCompleteName() + "    " + LocalDate.now());
                    if(!sUser.getPhotoUrl().trim().isEmpty()) {
                        new DownloadImageTask((ImageView) findViewById(R.id.user_icon))
                                .execute(sUser.getPhotoUrl());

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

        new Thread(new Runnable()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(3000);
                    abrirFragmentoMenu(new MainPageFragment());
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();

        btnMainNavigation = findViewById(R.id.btnMainNavigation);
        btnMainNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.menu_home) { abrirFragmentoMenu( new MainPageFragment()); return true;}
                if (item.getItemId() == R.id.menu_add_module) { abrirFragmentoMenu( new NewModuloFragment()); return true;}
                if (item.getItemId() == R.id.menu_config) { abrirFragmentoMenu( new ConfigParentFragment()); return true;}

                return true;
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

