package com.kin.counter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kin.counter.R;
import com.kin.counter.adapters.ListAdapter;
import com.kin.counter.database.DatabaseHelper;
import com.kin.counter.userDao.Counter;

import java.util.List;

public class CounterListActivity extends AppCompatActivity {
    public static List<Counter> counterList;
    public static DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_list);

        db = DatabaseHelper.getDatabaseHelper(getApplicationContext());
        counterList = db.queryForCounters();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listRecyclerView);
        ListAdapter listAdapter = new ListAdapter();
        recyclerView.setAdapter(listAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

}
