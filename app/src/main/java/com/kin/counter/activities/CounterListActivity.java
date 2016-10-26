package com.kin.counter.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    protected String searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_list);
    }

    protected abstract void refreshCounterList ();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_counter_list, menu);
        if (this instanceof NameSearchActivity) {
            MenuItem menuItem = menu.findItem(R.id.plusOneActionItem);
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            menuItem.setVisible(false);
        }
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

    protected AlertDialog createAlertDialog() {
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
                    searchString = searchStringEditText.getText().toString().trim();
                    dialog.dismiss();
                    if (CounterListActivity.this instanceof AllCounterListActivity) {
                        Intent intent = new Intent(CounterListActivity.this, NameSearchActivity.class);
                        intent.putExtra(NameSearchActivity.SEARCH_STRING, searchString);
                        startActivityForResult(intent, NAME_SEARCH_REQUEST);
                    }

                    else if (CounterListActivity.this instanceof NameSearchActivity) {
                        setTitle("Search Result For:   \"" + searchString + "\"");
                        refreshCounterList();
                    }
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
}
