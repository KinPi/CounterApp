package com.kin.counter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.kin.counter.R;
import com.kin.counter.adapters.ListAdapter;
import com.kin.counter.database.DatabaseHelper;
import com.kin.counter.userDao.Counter;

import java.util.List;


public abstract class CounterListActivity extends AppCompatActivity {
    public static final int NEW_COUNTER_ITEM_REQUEST = 1;
    public static final int NAME_SEARCH_REQUEST = 2;
    public static final String SEARCH_STRING = "searchString";

    public static DatabaseHelper db;
    protected List<Counter> counterList;
    protected ListAdapter listAdapter;
    protected RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_list);
    }

    protected void refreshCounterList () {
        counterList = db.queryForAllCounters();
        listAdapter.setCounterList(counterList);
        listAdapter.notifyDataSetChanged();
    }
}
