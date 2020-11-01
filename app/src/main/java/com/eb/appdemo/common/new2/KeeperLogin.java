package com.eb.appdemo.common.new2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.eb.appdemo.R;

public class KeeperLogin extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
    }

    public void callBackAl(View view) {
        Intent intent =  new Intent(getApplicationContext(),KeeperPresentationActivity.class);

        startActivity(intent);
    }

    public void callSignUp(View view) {
        Intent intent =  new Intent(getApplicationContext(),KeeperSignUpActivity.class);
        startActivity(intent);
    }
}
