package com.eb.appdemo.common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.eb.appdemo.R;

public class LoginStartUpScreen extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_start_up_screen);
    }

    public void callSignUpScreen(View view){
        Intent intent =  new Intent(getApplicationContext(),SignUp.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.btnSignUp),"transition_singup");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Log.i(TAG,"On version= " + android.os.Build.VERSION.SDK_INT) ;
            ActivityOptions opt = ActivityOptions.makeSceneTransitionAnimation(LoginStartUpScreen.this,pairs);
            startActivity(intent,opt.toBundle());
        } else {
            Log.i(TAG,"Off version= " + android.os.Build.VERSION.SDK_INT) ;
            startActivity(intent);
        }
    }

    public void callLoginScreen(View view){
        Intent intent =  new Intent(getApplicationContext(),Login.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.btnLogin),"transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Log.i(TAG,"On version= " + android.os.Build.VERSION.SDK_INT) ;
            ActivityOptions opt = ActivityOptions.makeSceneTransitionAnimation(LoginStartUpScreen.this,pairs);
            startActivity(intent,opt.toBundle());
        } else {
            Log.i(TAG,"Off version= " + android.os.Build.VERSION.SDK_INT) ;
            startActivity(intent);
        }
    }

}