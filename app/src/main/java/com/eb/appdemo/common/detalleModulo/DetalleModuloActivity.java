package com.eb.appdemo.common.detalleModulo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.eb.appdemo.R;
import com.eb.appdemo.common.config.ConfigParentFragment;
import com.eb.appdemo.common.modulo.MainPageFragment;
import com.eb.appdemo.common.modulo.NewModuloFragment;
import com.eb.appdemo.common.new2.KeeperMainPageActivity;
import com.eb.appdemo.entidades.SinglentonUtil;
import com.eb.appdemo.entidades.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

//TODO Investigate a way to do this on KeeperMainPageActivity, integrated in one
public class DetalleModuloActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    BottomNavigationView btnDetalleModuloNavigation;

    ImageView detalle_modulo_user_icon;
    MaterialTextView detalle_modulo_user_welcome;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_modulo);

        Log.i(TAG,"DetalleModuloActivity onCreate id = " +
                SinglentonUtil.singlentonUtil.getIdModulo()) ;

        hooks();

        updateHeader();

        setMenuAction();

    }

    private void hooks() {
        detalle_modulo_user_icon = findViewById(R.id.detalle_modulo_user_icon);
        detalle_modulo_user_welcome = findViewById(R.id.detalle_modulo_user_welcome);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateHeader() {
        detalle_modulo_user_welcome.setText("Bienvenido, " + User.singletonUser.getUserCompleteName() +
                "    " + LocalDate.now());
        if(!User.singletonUser.getPhotoUrl().trim().isEmpty()) {
            new DownloadImageTask((ImageView) findViewById(R.id.detalle_modulo_user_icon))
                    .execute(User.singletonUser.getPhotoUrl());

        } else {
            //TODO default user icon set
        }
    }

    //COMPLETED necesitar definir y aplicar nuevo menu para el detalle
    private void setMenuAction(){

        new Thread(() -> {
            try
            {
                Thread.sleep(3000);
                abrirFragmentoMenu(new TemperaturaChartFragment());
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }).start();

        btnDetalleModuloNavigation = findViewById(R.id.btnDetalleModuloNavigation);
        btnDetalleModuloNavigation.setOnNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.menu_temperatura) { abrirFragmentoMenu( new TemperaturaChartFragment()); return true;}
            if (item.getItemId() == R.id.menu_humedad) { abrirFragmentoMenu( new HumedadChartFragment()); return true;}
            if (item.getItemId() == R.id.menu_all) { abrirFragmentoMenu( new AllChartFragment()); return true;}
            if (item.getItemId() == R.id.menu_detalle_modulo) { abrirFragmentoMenu( new DetalleModeloFragment()); return true;}
            if (item.getItemId() == R.id.menu_return_home) { redirectHome();}

            return true;
        });
    }

    private void abrirFragmentoMenu(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.detalle_modulo_main_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    private void redirectHome(){
        Intent mainIntent = new Intent(this, KeeperMainPageActivity.class);
        startActivity(mainIntent);
    }

    //TODO create a util of this function
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "DetalleModuloActivity onRequestPermissionsResult requestCode = " + requestCode);
        Log.i(TAG, "DetalleModuloActivity onRequestPermissionsResult permissions[] = " + permissions.toString());
        Log.i(TAG, "DetalleModuloActivity onRequestPermissionsResult grantResults[] = " + grantResults.toString());
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }



}