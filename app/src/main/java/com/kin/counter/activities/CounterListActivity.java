package com.kin.counter.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    public void searchString (String searchString) {
        boolean found = false;
        for (Counter counter : counterList) {
            if (counter.name.equalsIgnoreCase(searchString)) {
                recyclerView.smoothScrollToPosition(counter.id - 1);
                found = true;
                break;
            }
        }

        if (!found) {
            Toast.makeText(this, searchString + " not found!", Toast.LENGTH_SHORT).show();
        }
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
                    searchString(searchString);
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
