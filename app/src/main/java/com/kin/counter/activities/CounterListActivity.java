package com.kin.counter.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kin.counter.R;
import com.kin.counter.adapters.ListAdapter;
import com.kin.counter.database.DatabaseHelper;
import com.kin.counter.userDao.Counter;

import java.util.ArrayList;
import java.util.List;

public class CounterListActivity extends AppCompatActivity {
    public static List<Counter> counterList;
    public static DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_list);

        counterList = new ArrayList<>(20);
        db = DatabaseHelper.getDatabaseHelper(getApplicationContext());
        Cursor result = doQuery();

        while (result.moveToNext()) {
            int id = result.getInt(0);
            String name =result.getString(1);
            int steps =result.getInt(2);
            // do something useful with these
            Log.d("DB: ", "id: " + id + "   Name: " + name + "     Steps: " + steps);
            counterList.add(new Counter(id, name, steps));
        }
        result.close();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listRecyclerView);
        ListAdapter listAdapter = new ListAdapter();
        recyclerView.setAdapter(listAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    protected Cursor doQuery() {
        Cursor result= db
                    .getReadableDatabase()
                    .query(DatabaseHelper.TABLE, null,
//                            new String[] {"ROWID AS _id",
//                                    DatabaseHelper.NAME,
//                                    DatabaseHelper.COUNT},
                            null, null, null, null, DatabaseHelper.NAME);

        result.getCount();

        return(result);
    }
}
