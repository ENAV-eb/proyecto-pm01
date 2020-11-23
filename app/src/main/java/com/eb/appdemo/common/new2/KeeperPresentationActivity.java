package com.eb.appdemo.common.new2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.eb.appdemo.R;
import com.eb.appdemo.db.KeeperDB;
import com.eb.appdemo.entidades.User;
import com.eb.appdemo.modelo.DAOUser;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class KeeperPresentationActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    //Init BD
    KeeperDB instance = KeeperDB.getInstance(this);
    DAOUser daoUser = new DAOUser();

    private FirebaseAuth mAuth;

    Button btnLogin,btnSignin;

    MaterialButton btnIngresar;

    LinearLayout login_layout, ingresar_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keeper_presentation);
        hooks();

        Log.i(TAG,"Init BD= " + instance.hashCode()) ;

        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("message", "Integración con firebase");
        bundle.putInt("init_bd",  instance.hashCode());

        firebaseAnalytics.logEvent("InitScreen",bundle);

        mAuth = FirebaseAuth.getInstance();

        callMain();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null)  updateUI(currentUser);



    }

    private void hooks(){
        btnLogin = findViewById(R.id.btnLogin);
        btnSignin = findViewById(R.id.btnSignUp);
        login_layout = findViewById(R.id.login_layout);
        ingresar_layout = findViewById(R.id.ingresar_layout);
        btnIngresar = findViewById(R.id.btnIngresar);
    }



    private void updateUI(FirebaseUser currentUser) {

        login_layout.setVisibility(View.GONE);
        User user = daoUser.getUserFromUID(currentUser.getUid());
        //TODO replace sqLite with a mysqldb or add firebase support for other params
        //btnIngresar.setText("Bienvenido " + user.getUserCompleteName() +  "! - Click para Ingresar");
        btnIngresar.setText("Bienvenido " + mAuth.getCurrentUser().getEmail() +  "! - Click para Ingresar");
        ingresar_layout.setVisibility(View.VISIBLE);

    }

    public void callMain() {

        btnIngresar.setOnClickListener(v -> {
            Intent mainIntent = new Intent(this,KeeperMainPageActivity.class);

            mainIntent.putExtra("first_name",mAuth.getCurrentUser().getEmail());
            mainIntent.putExtra("last_name","dummy");

            startActivity(mainIntent);
        });
    }

    public void callSignUpScreen(View view){
        Intent intent =  new Intent(getApplicationContext(),KeeperSignUpActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(btnSignin,"transition_singup");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Log.i(TAG,"On version= " + android.os.Build.VERSION.SDK_INT) ;
            ActivityOptions opt = ActivityOptions.makeSceneTransitionAnimation(KeeperPresentationActivity.this,pairs);
            startActivity(intent,opt.toBundle());
        } else {
            Log.i(TAG,"Off version= " + android.os.Build.VERSION.SDK_INT) ;
            startActivity(intent);
        }
    }

    public void callLoginScreen(View view){
        Intent intent =  new Intent(getApplicationContext(),KeeperLogin.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(btnLogin,"transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Log.i(TAG,"On version= " + android.os.Build.VERSION.SDK_INT) ;
            ActivityOptions opt = ActivityOptions.makeSceneTransitionAnimation(KeeperPresentationActivity.this,pairs);
            startActivity(intent,opt.toBundle());
        } else {
            Log.i(TAG,"Off version= " + android.os.Build.VERSION.SDK_INT) ;
            startActivity(intent);
        }
    }
}