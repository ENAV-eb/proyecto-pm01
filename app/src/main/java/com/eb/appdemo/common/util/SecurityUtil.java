package com.eb.appdemo.common.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.eb.appdemo.R;
import com.eb.appdemo.common.new2.KeeperMainPageActivity;
import com.eb.appdemo.common.new2.KeeperPresentationActivity;
import com.eb.appdemo.entidades.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SecurityUtil {

    private static final String TAG = "MyActivity";
    private int GOOGLE_SIGN_IN = 100;

    public static void loginFirebaseUser(String username, String password, Context context) {
        if(!username.isEmpty() && !password.isEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    username, password).addOnCompleteListener(command -> {
                redirectMainPage(context);
            });
            //TODO Añadir en caso de fallo y cancelación
        } //TODO ALERTDialog in case missing porameter


    }

    public static void createUserGoogleToDB(GoogleSignInAccount account, ProviderType providerType,
                                           Context context) {

        User user =  new User(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                account.getGivenName(),account.getFamilyName(),account.getEmail(),
                "","",providerType.toString(),
                account.getPhotoUrl().toString());

        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference();

        userReference.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user);

        redirectMainPage(context);

    }

    public static void createFirebaseUser(User user,String password ,ProviderType providerType,
                                   Context context) {
        if(user != null) {
            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(user.getEmail()
                            ,password)
                    .addOnCompleteListener(command -> {
                        if(command.isSuccessful()) {
                            //registerSqLiteUser(user, ProviderType.BASIC);
                            createUserSignFormToDB(user,ProviderType.BASIC,context);
                        }
                        else { AlertDialogUtil
                                .showAlertDialog("Se ha producido un error de autenticación",
                                        context,"Error");}
                    });
        }
    }

    private static void createUserSignFormToDB(User user, ProviderType providerType,
                                              Context context){

        user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        user.setProviderType(providerType.toString());

        if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()!=null) {
            user.setPhotoUrl(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
        } else {
            user.setPhotoUrl("");
        }

        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference();

        userReference.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user);

        redirectMainPage(context);
    }


    public boolean verifiedMasterKey(String key){

        final boolean[] flag = {false};

        Query keyQuery = FirebaseDatabase.getInstance()
                .getReference()
                .orderByChild("key")
                .equalTo(key);

        keyQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i(TAG, "verifiedMasterKey count:" + dataSnapshot.getChildrenCount());

                if(dataSnapshot.getChildrenCount()==1) flag[0] = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

        Log.i(TAG, "verifiedMasterKey flag:" + flag[0]);

        return flag[0];
    }


    private static void redirectMainPage(Context context){
        Intent mainIntent = new Intent(context, KeeperMainPageActivity.class);
        context.startActivity(mainIntent);
    }

    private void redirectPresentationPage(Context context){
        Intent mainIntent = new Intent(context, KeeperPresentationActivity.class);
        context.startActivity(mainIntent);
    }


}
