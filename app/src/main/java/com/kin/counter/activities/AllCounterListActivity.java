package com.kin.counter.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kin.counter.R;
import com.kin.counter.adapters.ListAdapter;
import com.kin.counter.database.DatabaseHelper;

public class AllCounterListActivity extends CounterListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = DatabaseHelper.getDatabaseHelper(getApplicationContext());
        counterList = db.queryForAllCounters();

        recyclerView = (RecyclerView) findViewById(R.id.listRecyclerView);
        listAdapter = new ListAdapter(this, counterList);
        recyclerView.setAdapter(listAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == NEW_COUNTER_ITEM_REQUEST) {
//
//            if (resultCode == RESULT_OK) {
//                long id = data.getLongExtra("Id", -1);
//                if (id != -1) {
//                    refreshCounterList();
//                    recyclerView.scrollToPosition(getPositionGivenID((int) id));
//                }
//            }
//        }
//
//        else if (requestCode == NAME_SEARCH_REQUEST) {
//            refreshCounterList();
//            navigationView.getMenu().getItem(getOrderByIndex()).setChecked(true);
//        }
//    }
//
//    private int getPositionGivenID (int id) {
//        for (int i = 0; i < counterList.size(); i++) {
//            if (counterList.get(i).id == id) {
//                return i;
//            }
//        }
//        return 1;
//    }

    @Override
    protected void refreshCounterList () {
        counterList = db.queryForAllCounters();
        listAdapter.setCounterList(counterList);
        listAdapter.notifyDataSetChanged();
    }

}
