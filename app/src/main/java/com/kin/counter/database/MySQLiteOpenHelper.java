package com.kin.counter.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "counter.db";
    private static final int SCHEMA = 1;
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String COUNT = "count";
    public static final String STEP = "step";
    public static final String TABLE = "counter";

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE counter (id INTEGER PRIMARY KEY, name TEXT, count INTEGER, step INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new RuntimeException("No upgrade!");
    }
}
