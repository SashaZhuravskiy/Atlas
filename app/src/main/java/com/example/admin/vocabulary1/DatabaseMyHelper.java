package com.example.admin.vocabulary1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 06.04.2017.
 */

public class DatabaseMyHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Country";
    public static final String TABLE_COUNTRY  = "Country";

    public static final String KEY_ID = "_id";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_CAPITAL = "capital";

    public DatabaseMyHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_COUNTRY + " ("+ KEY_ID +
        " integer primary key, " + KEY_COUNTRY + " text, " + KEY_CAPITAL +
        " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_COUNTRY);
        onCreate(db);
    }
}
