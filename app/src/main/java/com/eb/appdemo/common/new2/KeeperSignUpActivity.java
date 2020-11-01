package com.eb.appdemo.common.new2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.eb.appdemo.R;

public class KeeperSignUpActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keeper_sign_up);
    }

    public void callCreateAccount(View view) {}

    //asp_back_button
//
    public void callBackSU(View view) {
        Intent intent =  new Intent(getApplicationContext(),KeeperPresentationActivity.class);

        startActivity(intent);
    }

    public void callLogin(View view) {
        //transition_login_btn
        Intent intent =  new Intent(getApplicationContext(),KeeperLogin.class);
        startActivity(intent);

    }
}