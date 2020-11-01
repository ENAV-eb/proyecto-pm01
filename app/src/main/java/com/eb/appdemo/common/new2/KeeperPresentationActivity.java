package com.eb.appdemo.common.new2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.eb.appdemo.R;
import com.eb.appdemo.common.LoginSignup.Login;
import com.eb.appdemo.common.LoginSignup.LoginStartUpScreen;
import com.eb.appdemo.common.LoginSignup.SignUp;

public class KeeperPresentationActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keeper_presentation);
    }

    public void callSignUpScreen(View view){
        Intent intent =  new Intent(getApplicationContext(),KeeperSignUpActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.btnSignUp),"transition_singup");

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
        pairs[0] = new Pair<View,String>(findViewById(R.id.btnLogin),"transition_login");

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