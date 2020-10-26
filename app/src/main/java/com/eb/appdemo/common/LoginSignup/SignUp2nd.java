package com.eb.appdemo.common.LoginSignup;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.eb.appdemo.R;

public class SignUp2nd extends AppCompatActivity {

    //Variables
    DatePicker datePicker;
    RadioButton maleRb,femaleRb;

    ImageView backBtn;
    Button next, login;
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2nd);

        //Hooks
        backBtn = findViewById(R.id.signup2nd_back_button);
        next = findViewById(R.id.signup2nd_next_button);
        login = findViewById(R.id.signup2nd_login_button);
        titleText = findViewById(R.id.signup2nd_title_text);
    }

    public void callNextSignUp3rdScreen(View view) {

        Intent intent = new Intent(getApplicationContext(),SignUp3rd.class);

        //Add transitions
        Pair[] pairs = new Pair[4];

        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(next, "transition_next_btn");
        pairs[2] = new Pair<View, String>(login, "transition_login_btn");
        pairs[3] = new Pair<View, String>(titleText, "transition_title_text");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions opt = ActivityOptions.makeSceneTransitionAnimation(SignUp2nd.this, pairs);
            startActivity(intent, opt.toBundle());
        } else {
            startActivity(intent);
        }
    }
}