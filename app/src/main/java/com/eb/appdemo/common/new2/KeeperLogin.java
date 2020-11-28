package com.eb.appdemo.common.new2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.eb.appdemo.R;
import com.eb.appdemo.common.util.ProviderType;
import com.eb.appdemo.common.util.SecurityUtil;
import com.eb.appdemo.entidades.User;
import com.eb.appdemo.modelo.DAOUser;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class KeeperLogin extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    private MaterialButton al_login,btnForgotPassword;
    private TextInputEditText inputUsername, inputPassword;
    private MaterialCheckBox chkRememberMe;

    private String username, password;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private DAOUser daoUser = new DAOUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getSupportActionBar().hide();

        setContentView(R.layout.activity_login_new);

        hooks();

        settingLoginAction();
    }

    private void hooks(){

        al_login = findViewById(R.id.al_login);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        chkRememberMe = findViewById(R.id.chkRememberMe);
        inputUsername = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);

    }

    private void settingLoginAction(){

        al_login.setOnClickListener(v -> {
            SecurityUtil.loginFirebaseUser(inputUsername.getText().toString(),
                    inputPassword.getText().toString(),this);
            /*
            if(!inputUsername.getText().toString().isEmpty() &&
                    !inputPassword.getText().toString().isEmpty()) {
                FirebaseAuth.getInstance().
                        signInWithEmailAndPassword(inputUsername.getText().toString(),
                                inputPassword.getText().toString())
                        .addOnCompleteListener(command -> {

                                redirectMainPage();

                        });
            }

             */
        });

    }

    private void redirectMainPage() {


        Intent mainIntent = new Intent(this,KeeperMainPageActivity.class);


        startActivity(mainIntent);
    }


    public void callBackAl(View view) {
        Intent intent =  new Intent(getApplicationContext(),KeeperPresentationActivity.class);

        startActivity(intent);
    }

    public void callSignUp(View view) {
        Intent intent =  new Intent(getApplicationContext(),KeeperSignUpActivity.class);
        startActivity(intent);
    }
    //al_login
    public void callLogin(View view) {
        Intent intent =  new Intent(getApplicationContext(),KeeperMainPageActivity.class);
        startActivity(intent);
    }
}
