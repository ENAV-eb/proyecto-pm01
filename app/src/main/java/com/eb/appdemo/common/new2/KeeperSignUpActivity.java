package com.eb.appdemo.common.new2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.eb.appdemo.R;
import com.eb.appdemo.common.util.AlertDialogUtil;
import com.eb.appdemo.common.util.Constantes;
import com.eb.appdemo.entidades.User;
import com.eb.appdemo.modelo.DAOUser;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hbb20.CountryCodePicker;

public class KeeperSignUpActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    private TextInputEditText rg_first_name,rg_last_name,rg_email,rg_phone_number,rg_password;

    private CountryCodePicker rg_country_picker;

    private MaterialButton signUp_action,signup_login_button;

    private ImageView asp_back_button;

    DAOUser daoUser = new DAOUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keeper_sign_up);

        hooks();

        settingBasicActions();

        settingRegisterAction();

    }

    private void hooks(){

        rg_first_name = findViewById(R.id.rg_first_name);
        rg_last_name = findViewById(R.id.rg_last_name);
        rg_email = findViewById(R.id.rg_email);
        rg_phone_number = findViewById(R.id.rg_phone_number);
        rg_password = findViewById(R.id.rg_password);

        rg_country_picker = findViewById(R.id.rg_country_picker);

        signUp_action = findViewById(R.id.signUp_action);
        signup_login_button = findViewById(R.id.signup_login_button);

        asp_back_button = findViewById(R.id.asp_back_button);


    }

    private void settingBasicActions(){

        signup_login_button.setOnClickListener(v -> {
            Intent intent =  new Intent(getApplicationContext(),KeeperLogin.class);
            startActivity(intent);
        });

        asp_back_button.setOnClickListener(v -> {
            Intent intent =  new Intent(getApplicationContext(),KeeperPresentationActivity.class);
            startActivity(intent);
        });

    }

    private void settingRegisterAction(){

        signUp_action.setOnClickListener(v -> {

            User user = generatedTmpUser();

            if(user != null) {
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(user.getEmail()
                                ,rg_password.getText().toString())
                        .addOnCompleteListener(command -> {
                            if(command.isSuccessful()) {
                                registerSqLiteUser(user);
                            }
                            else { AlertDialogUtil
                                    .showAlertDialog("Se ha producido un error de autenticación",
                                            this,"Error");}
                        });
            }

        });

    }


    private void registerSqLiteUser(User user) {

        user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());

        daoUser.openDB();

        daoUser.registrarUsuario(user);

         //TODO Falta guardar USER en shared preference


        Intent mainIntent = new Intent(this,KeeperMainPageActivity.class);

        mainIntent.putExtra("first_name",user.getFirstName());
        mainIntent.putExtra("last_name",user.getLastName());

        startActivity(mainIntent);

    }

    private User generatedTmpUser() {

        String first_name = rg_first_name.getText().toString();
        String last_name = rg_last_name.getText().toString();
        String email = rg_email.getText().toString();
        String phone =  rg_phone_number.getText().toString();
        String country = rg_country_picker.getSelectedCountryCode();
        String password = rg_password.getText().toString();

        String[] verify = {first_name,last_name,email,phone,country,password};
        int counter = 0;
        boolean flag = true;

        for (String s: verify) {
            if(s == null | s.trim().isEmpty()) {
                AlertDialogUtil.showAlertDialog(Constantes.USER_ERROR_LABEL[counter],this,
                        Constantes.USER_ERROR_TITLE);
                flag = false;
                break;
            }
            counter++;
        }

        if (flag)  return new User(first_name, last_name, email, phone, country);
        else return null;

    }




}