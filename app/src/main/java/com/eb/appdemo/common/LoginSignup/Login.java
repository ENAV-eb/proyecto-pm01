package com.eb.appdemo.common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.eb.appdemo.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);
    }
}