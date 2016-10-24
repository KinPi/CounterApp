package com.kin.counter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kin.counter.userDao.Counter;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "counter.db";
    private static final int SCHEMA = 1;
    public static final String NAME ="name";
    public static final String COUNT ="count";
    public static final String TABLE="counter";
    private static DatabaseHelper mDatabaseHelper;

    public DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    public static DatabaseHelper getDatabaseHelper (Context context) {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = new DatabaseHelper(context);
        }
        return mDatabaseHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE counter (id INTEGER PRIMARY KEY, name TEXT, count INTEGER);");

        ContentValues cv=new ContentValues();

        cv.put(NAME, "Burps");
        cv.put(COUNT, 10);
        db.insert(TABLE, NAME, cv);

        cv.put(NAME, "Kisses");
        cv.put(COUNT, 1);
        db.insert(TABLE, NAME, cv);

        cv.put(NAME, "Jumps");
        cv.put(COUNT, 15);
        db.insert(TABLE, NAME, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new RuntimeException("How did this happen?");
    }

    public int update (Counter counter) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, counter.name);
        cv.put(COUNT, counter.count);
        return db.update(TABLE, cv, "id = " + counter.id ,null);
    }
}
