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
import com.eb.appdemo.common.util.AlertDialogUtil;
import com.eb.appdemo.common.util.ProviderType;
import com.eb.appdemo.db.KeeperDB;
import com.eb.appdemo.entidades.User;
import com.eb.appdemo.modelo.DAOUser;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.shobhitpuri.custombuttons.GoogleSignInButton;


public class KeeperPresentationActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    private int GOOGLE_SIGN_IN = 100;

    //Init BD
    KeeperDB instance = KeeperDB.getInstance(this);
    DAOUser daoUser = new DAOUser();

    private FirebaseAuth mAuth;

    Button btnLogin,btnSignin;

    MaterialButton btnIngresar;

    GoogleSignInButton sign_in_google_button;

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
        setGoogleAuth();

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

        sign_in_google_button = findViewById(R.id.sign_in_google_button);
    }

    private void setGoogleAuth(){

        sign_in_google_button.setOnClickListener(v -> {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
            //TODO enable log out on presentation page
            mGoogleSignInClient.signOut();
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN);


        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());


                if(account!= null) {
                    AuthCredential credential = GoogleAuthProvider
                            .getCredential(account.getIdToken(),null);
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener(command -> {
                        if(command.isSuccessful()) {
                            obtainUserData(account, ProviderType.GOOGLE);
                        } else {
                            AlertDialogUtil
                                    .showAlertDialog("Se ha producido un error de autenticación",
                                            this,"Error");}
                    });
                }

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void obtainUserData(GoogleSignInAccount account,ProviderType providerType) {

        User user =  new User(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                account.getDisplayName(),account.getFamilyName(),account.getEmail(),
                "","");

        //TODO IMAGE profile

        daoUser.openDB();
        daoUser.registrarUsuario(user);


        Intent mainIntent = new Intent(this,KeeperMainPageActivity.class);

        mainIntent.putExtra("uid",user.getId());
        mainIntent.putExtra("first_name",user.getFirstName());
        mainIntent.putExtra("last_name",user.getLastName());
        mainIntent.putExtra("providerType",providerType);

        startActivity(mainIntent);
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