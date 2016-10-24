package com.kin.counter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kin.counter.userDao.Counter;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static DatabaseHelper mDatabaseHelper;
    private MySQLiteOpenHelper mMySQLiteOpenHelper;

    private DatabaseHelper (Context context) {
        mMySQLiteOpenHelper = new MySQLiteOpenHelper(context);
    }

    public static DatabaseHelper getDatabaseHelper(Context context) {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = new DatabaseHelper(context);
        }
        return mDatabaseHelper;
    }

    class MySQLiteOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "counter.db";
        private static final int SCHEMA = 1;
        public static final String NAME = "name";
        public static final String ID = "id";
        public static final String COUNT = "count";
        public static final String TABLE = "counter";

        public MySQLiteOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, SCHEMA);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE counter (id INTEGER PRIMARY KEY, name TEXT, count INTEGER);");

            ContentValues cv = new ContentValues();

            cv.put(NAME, "Burps");
            cv.put(COUNT, 10);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Kisses");
            cv.put(COUNT, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Jumps");
            cv.put(COUNT, 15);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Burps");
            cv.put(COUNT, 10);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Kisses");
            cv.put(COUNT, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Jumps");
            cv.put(COUNT, 15);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Burps");
            cv.put(COUNT, 10);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Kisses");
            cv.put(COUNT, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Jumps");
            cv.put(COUNT, 15);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Burps");
            cv.put(COUNT, 10);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Kisses");
            cv.put(COUNT, 1);
            db.insert(TABLE, NAME, cv);

            cv.put(NAME, "Jumps");
            cv.put(COUNT, 15);
            db.insert(TABLE, NAME, cv);

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

    }

    public List<Counter> queryForCounters () {
        SQLiteDatabase db = mMySQLiteOpenHelper.getReadableDatabase();
        Cursor result = db.query(mMySQLiteOpenHelper.TABLE, null, null, null, null, null, mMySQLiteOpenHelper.ID);
        List<Counter> counterList = new ArrayList<>(20);

        while (result.moveToNext()) {
            int id = result.getInt(0);
            String name =result.getString(1);
            int steps =result.getInt(2);
            Log.d("DB: ", "id: " + id + "   Name: " + name + "     Steps: " + steps);
            counterList.add(new Counter(id, name, steps));
        }
        result.close();

        return counterList;
    }

    public int update (Counter counter) {
        SQLiteDatabase db = mMySQLiteOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(mMySQLiteOpenHelper.NAME, counter.name);
        cv.put(mMySQLiteOpenHelper.COUNT, counter.count);
        return db.update(mMySQLiteOpenHelper.TABLE, cv, "id = " + counter.id ,null);
    }
}
