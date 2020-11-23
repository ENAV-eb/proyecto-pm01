package com.eb.appdemo.common.new2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.eb.appdemo.R;
import com.eb.appdemo.entidades.User;
import com.eb.appdemo.modelo.DAOUser;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class KeeperLogin extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    private MaterialButton al_login,btnForgotPassword;
    private TextInputEditText inputUsername, inputPassword;
    private MaterialCheckBox chkRememberMe;

    private String username, password;

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
            if(!inputUsername.getText().toString().isEmpty() &&
                    !inputPassword.getText().toString().isEmpty()) {
                FirebaseAuth.getInstance().
                        signInWithEmailAndPassword(inputUsername.getText().toString(),
                                inputPassword.getText().toString())
                        .addOnCompleteListener(command -> {

                                obtainUserData();

                        });
            }
        });

    }

    private void obtainUserData() {

        daoUser.openDB();

        User user = daoUser.getUserFromUID(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //TODO Falta guardar USER en shared preference


        Intent mainIntent = new Intent(this,KeeperMainPageActivity.class);

        mainIntent.putExtra("first_name",user.getFirstName());
        mainIntent.putExtra("last_name",user.getLastName());

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
