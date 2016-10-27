package com.kin.counter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kin.counter.Settings;


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

        if (Settings.DEBUG_MODE) {
            ContentValues cv = new ContentValues();

            cv.put(NAME, "Apples");
            cv.put(COUNT, 0);
            cv.put(STEP, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Bananas");
            cv.put(COUNT, 0);
            cv.put(STEP, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Pumpkins");
            cv.put(COUNT, 0);
            cv.put(STEP, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Pears");
            cv.put(COUNT, 0);
            cv.put(STEP, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Oranges");
            cv.put(COUNT, 0);
            cv.put(STEP, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Watermelons");
            cv.put(COUNT, 0);
            cv.put(STEP, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Grapes");
            cv.put(COUNT, 0);
            cv.put(STEP, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Squashes");
            cv.put(COUNT, 0);
            cv.put(STEP, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Carrots");
            cv.put(COUNT, 0);
            cv.put(STEP, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Peaches");
            cv.put(COUNT, 0);
            cv.put(STEP, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Lemons");
            cv.put(COUNT, 0);
            cv.put(STEP, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Strawberries");
            cv.put(COUNT, 0);
            cv.put(STEP, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Cabbages");
            cv.put(COUNT, 0);
            cv.put(STEP, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Tomatoes");
            cv.put(COUNT, 0);
            cv.put(STEP, 1);
            db.insert(TABLE, NAME, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new RuntimeException("No upgrade!");
    }
}
