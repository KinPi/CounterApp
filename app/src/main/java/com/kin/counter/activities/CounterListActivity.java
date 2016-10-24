package com.kin.counter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.kin.counter.R;
import com.kin.counter.adapters.ListAdapter;
import com.kin.counter.database.DatabaseHelper;
import com.kin.counter.userDao.Counter;

import java.util.List;

public class CounterListActivity extends AppCompatActivity {
    public static final int NEW_COUNTER_ITEM_REQUEST = 1;
    public static List<Counter> counterList;
    public static DatabaseHelper db;
    private ListAdapter listAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_list);

        db = DatabaseHelper.getDatabaseHelper(getApplicationContext());
        counterList = db.queryForAllCounters();

        recyclerView = (RecyclerView) findViewById(R.id.listRecyclerView);
        listAdapter = new ListAdapter();
        recyclerView.setAdapter(listAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_counterlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.plusOneListItem:
                Intent intent = new Intent(this, NewCounterItemActivity.class);
                startActivityForResult(intent, NEW_COUNTER_ITEM_REQUEST);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == NEW_COUNTER_ITEM_REQUEST) {

            if (resultCode == RESULT_OK) {
                counterList = db.queryForAllCounters();
                listAdapter.notifyDataSetChanged();
                long id = data.getLongExtra("Id", -1);
                if (id != -1) {
                    recyclerView.scrollToPosition(((int) id) - 1);
                }
            }
        }
    }
}
