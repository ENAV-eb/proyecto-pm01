package com.eb.appdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.eb.appdemo.common.util.Constantes;

public class KeeperDB extends SQLiteOpenHelper {

    public static KeeperDB sSoleInstance;

    public static KeeperDB getInstance(Context context) {
        if (sSoleInstance == null){ //if there is no instance available... create new one
            sSoleInstance = new KeeperDB(context);
        }

        return sSoleInstance;
    }

    public static SQLiteDatabase openDB() {
        return sSoleInstance.getWritableDatabase();
    }

    public static void closeDB(){
        sSoleInstance.close();
    }



    private KeeperDB(@Nullable Context context) {
        super(context, Constantes.NOMBRE_BD, null, Constantes.VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tables_query = createUserTable();

        db.execSQL(tables_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private String  createUserTable(){

        String query =
                "CREATE TABLE USER " +
                        " (id TEXT PRIMARY KEY," +
                        " first_name TEXT NOT NULL," +
                        " last_name TEXT NOT NULL," +
                        " email TEXT NOT NULL," +
                        " phone TEXT NOT NULL," +
                        " country_code TEXT NOT NULL); " +
                        " ";

        return query;
    }
}
