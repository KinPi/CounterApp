package com.kin.counter.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kin.counter.R;
import com.kin.counter.Settings;
import com.kin.counter.adapters.ListAdapter;
import com.kin.counter.database.DatabaseHelper;
import com.kin.counter.userDao.Counter;

import java.util.List;


public abstract class CounterListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int NEW_COUNTER_ITEM_REQUEST = 1;
    public static final int NAME_SEARCH_REQUEST = 2;
    public static final int COUNTER_ITEM_REQUEST = 3;
    public static final String SEARCH_STRING = "searchString";

    public static DatabaseHelper db;
    protected List<Counter> counterList;
    protected ListAdapter listAdapter;
    protected RecyclerView recyclerView;
    protected NavigationView navigationView;
    protected String searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_list);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(getOrderByIndex()).setChecked(true);
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
        View alertLayout = getLayoutInflater().inflate(R.layout.alert_dialog_search, null);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.earliest) {
            Log.d("TAG", "Pressed the camera!");
            Settings.CURRENT_ORDER_BY_CONFIG = Settings.ORDER_BY_EARLIEST;
        } else if (id == R.id.latest) {
            Settings.CURRENT_ORDER_BY_CONFIG = Settings.ORDER_BY_LATEST;
        } else if (id == R.id.alphabetical) {
            Settings.CURRENT_ORDER_BY_CONFIG = Settings.ORDER_BY_NAME_ASC;
        } else if (id == R.id.reverseAlphabetical) {
            Settings.CURRENT_ORDER_BY_CONFIG = Settings.ORDER_BY_NAME_DESC;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        refreshCounterList();
        return true;
    }

    protected int getOrderByIndex () {
        int index = -1;
        switch (Settings.CURRENT_ORDER_BY_CONFIG) {
            case Settings.ORDER_BY_EARLIEST:
                index = 0;
                break;
            case Settings.ORDER_BY_LATEST:
                index = 1;
                break;
            case Settings.ORDER_BY_NAME_ASC:
                index = 2;
                break;
            case Settings.ORDER_BY_NAME_DESC:
                index = 3;
                break;
        }
        return index;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == NEW_COUNTER_ITEM_REQUEST) {

            if (resultCode == RESULT_OK) {
                long id = data.getLongExtra("Id", -1);
                if (id != -1) {
                    refreshCounterList();
                    recyclerView.scrollToPosition(getPositionGivenID((int) id));
                }
            }
        }

        else if (requestCode == NAME_SEARCH_REQUEST) {
            refreshCounterList();
            navigationView.getMenu().getItem(getOrderByIndex()).setChecked(true);
        }

        else if (requestCode == COUNTER_ITEM_REQUEST) {
            refreshCounterList();
        }
    }

    protected int getPositionGivenID (int id) {
        for (int i = 0; i < counterList.size(); i++) {
            if (counterList.get(i).id == id) {
                return i;
            }
        }
        return 1;
    }
}
