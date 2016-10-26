package com.kin.counter.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kin.counter.R;
import com.kin.counter.adapters.ListAdapter;
import com.kin.counter.database.DatabaseHelper;

public class NameSearchActivity extends CounterListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = DatabaseHelper.getDatabaseHelper(getApplicationContext());
        searchString = getIntent().getStringExtra(SEARCH_STRING);
        setTitle("Search Result For:   \"" + searchString + "\"");
        counterList = db.queryForNameSearch(searchString);

        recyclerView = (RecyclerView) findViewById(R.id.listRecyclerView);
        listAdapter = new ListAdapter(counterList);
        recyclerView.setAdapter(listAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void refreshCounterList () {
        counterList = db.queryForNameSearch(searchString);
        listAdapter.setCounterList(counterList);
        listAdapter.notifyDataSetChanged();
    }


}
