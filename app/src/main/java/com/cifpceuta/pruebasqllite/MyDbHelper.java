package com.cifpceuta.pruebasqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "usuario.db";


    public MyDbHelper(Context context){

        super(context, DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + AreaContract.AreaEntry.TABLE_NAME + " (" +
                AreaContract.AreaEntry.COLUMN_USUARIO + " TEXT PRIMARY KEY," +
                AreaContract.AreaEntry.COLUMN_PASSW + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AreaContract.AreaEntry.TABLE_NAME);
        onCreate(db);

    }
}
