package com.kin.counter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kin.counter.Settings;
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

    public List<Counter> performCounterQuery (String namePattern) {
        String selection = null;
        if (namePattern != null) {
            selection = MySQLiteOpenHelper.NAME + " LIKE " + "'%" + namePattern.toLowerCase() + "%'";
        }
        SQLiteDatabase db = mMySQLiteOpenHelper.getReadableDatabase();
        Cursor result = db.query(mMySQLiteOpenHelper.TABLE, null, selection, null, null, null, Settings.CURRENT_ORDER_BY_CONFIG);
        List<Counter> counterList = new ArrayList<>();

        while (result.moveToNext()) {
            int id = result.getInt(0);
            String name = result.getString(1);
            int count = result.getInt(2);
            int increment = result.getInt(3);
            counterList.add(new Counter(id, name, count, increment));
        }
        result.close();
        db.close();

        return counterList;
    }

    public List<Counter> queryForNameSearch (String namePattern) {
        return performCounterQuery(namePattern);
    }

    public List<Counter> queryForAllCounters() {
        return performCounterQuery(null);
    }

    public int update (Counter counter) {
        SQLiteDatabase db = mMySQLiteOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(mMySQLiteOpenHelper.NAME, counter.name);
        cv.put(mMySQLiteOpenHelper.COUNT, counter.count);
        cv.put(mMySQLiteOpenHelper.INCREMENT, counter.increment);
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
        cv.put(mMySQLiteOpenHelper.INCREMENT, counter.increment);
        long id = db.insert(MySQLiteOpenHelper.TABLE, null, cv);
        db.close();
        return id;
    }

    public int delete (Counter counter) {
        SQLiteDatabase db = mMySQLiteOpenHelper.getWritableDatabase();
        if (db == null) {
            return 0;
        }
        return db.delete(MySQLiteOpenHelper.TABLE, MySQLiteOpenHelper.ID + "=?", new String[] {Integer.toString(counter.id)});
    }
}
