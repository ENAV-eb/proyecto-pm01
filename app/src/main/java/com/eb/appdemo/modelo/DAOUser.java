package com.eb.appdemo.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eb.appdemo.db.KeeperDB;
import com.eb.appdemo.entidades.User;

public class DAOUser {

    SQLiteDatabase sqlDb;

    public DAOUser() {}

    private static final String TAG = "MyActivity ";

    public void openDB(){sqlDb = KeeperDB.openDB();}

    public void registrarUsuario(User user) {

        try {

            ContentValues values = new ContentValues();
            values.put("id",user.getId());
            values.put("first_name",user.getFirstName());
            values.put("last_name",user.getLastName());
            values.put("email",user.getEmail());
            values.put("phone",user.getMobilePhone());
            values.put("country_code",user.getCountryCode());

            long result = sqlDb.insert(" USER ",
                    null,values);

            if (result == -1){
                Log.i(TAG, "Insert error of " + user.getId());
            }else{
                Log.i(TAG, "Success insert of " + user.getId());
            }

        } catch (Exception e) {
            Log.e(TAG, "Error insert of " + user.getId());
        }

    }

    public User getUserFromUID(String UID) {
        User user = new User();
        try {

            Cursor cursor = sqlDb.rawQuery("select * from USER where id = " + UID,null);

            while(cursor.moveToNext()){

                user = new User(cursor.getString(0)
                        ,cursor.getString(1)
                        ,cursor.getString(2)
                        ,cursor.getString(3)
                        ,cursor.getString(4)
                        ,cursor.getString(5));

            }

        } catch (Exception e) {

            user.setFirstName("NPC01");
            user.setLastName("WOKE");

        }

        return user;
    }
}
