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

    public List<Counter> queryForAllCounters() {
        SQLiteDatabase db = mMySQLiteOpenHelper.getReadableDatabase();
        Cursor result = db.query(mMySQLiteOpenHelper.TABLE, null, null, null, null, null, mMySQLiteOpenHelper.ID);
        List<Counter> counterList = new ArrayList<>(20);

        while (result.moveToNext()) {
            int id = result.getInt(0);
            String name = result.getString(1);
            int count = result.getInt(2);
            int step = result.getInt(3);
            Log.d("DB: ", "id: " + id + "   Name: " + name + "     Steps: " + count);
            counterList.add(new Counter(id, name, count, step));
        }
        result.close();
        db.close();

        return counterList;
    }

    public int update (Counter counter) {
        SQLiteDatabase db = mMySQLiteOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(mMySQLiteOpenHelper.NAME, counter.name);
        cv.put(mMySQLiteOpenHelper.COUNT, counter.count);
        cv.put(mMySQLiteOpenHelper.STEP, counter.step);
        int rowsAffected = db.update(mMySQLiteOpenHelper.TABLE, cv, "id = " + counter.id ,null);
        db.close();
        return rowsAffected;
    }

    public long add (Counter counter) {
        SQLiteDatabase db = mMySQLiteOpenHelper.getWritableDatabase();
        if (db == null) {
            return 0;
        }
        ContentValues cv = new ContentValues();
        cv.put(mMySQLiteOpenHelper.NAME, counter.name);
        cv.put(mMySQLiteOpenHelper.COUNT, counter.count);
        cv.put(mMySQLiteOpenHelper.STEP, counter.step);
        long id = db.insert(MySQLiteOpenHelper.TABLE, null, cv);
        db.close();
        return id;
    }
}
