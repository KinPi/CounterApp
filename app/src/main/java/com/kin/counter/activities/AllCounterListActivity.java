package com.kin.counter.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        listAdapter = new ListAdapter(counterList);
        recyclerView.setAdapter(listAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_counterlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.plusOneActionItem:
                Intent intent = new Intent(this, NewCounterItemActivity.class);
                startActivityForResult(intent, NEW_COUNTER_ITEM_REQUEST);
                break;

            case R.id.searchActionItem:
                AlertDialog dialog = createAlertDialog();
                dialog.show();
                break;
        }
        return true;
    }

    private AlertDialog createAlertDialog() {
        View alertLayout = getLayoutInflater().inflate(R.layout.search_alert_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search");
        builder.setView(alertLayout);

        final EditText searchStringEditText = (EditText) alertLayout.findViewById(R.id.SearchEditText);
        TextView searchTextView = (TextView) alertLayout.findViewById(R.id.searchDialogTextView);
        TextView cancelTextView = (TextView) alertLayout.findViewById(R.id.cancelDialogTextView);

        final AlertDialog dialog = builder.create();

        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchStringEditText.getText().length() > 0) {
                    String searchString = searchStringEditText.getText().toString();
                    dialog.dismiss();
                    Intent intent = new Intent(AllCounterListActivity.this, NameSearchActivity.class);
                    intent.putExtra(NameSearchActivity.SEARCH_STRING, searchString);
                    startActivityForResult(intent, NAME_SEARCH_REQUEST);
                }
            }
        });

        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == NEW_COUNTER_ITEM_REQUEST) {

            if (resultCode == RESULT_OK) {
                long id = data.getLongExtra("Id", -1);
                if (id != -1) {
                    refreshCounterList();
                    recyclerView.scrollToPosition(((int) id) - 1);
                }
            }
        }

        else if (requestCode == NAME_SEARCH_REQUEST) {
            refreshCounterList();
        }
    }

}
