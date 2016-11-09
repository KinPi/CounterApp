package com.kin.counter.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kin.counter.CounterStringChecker;
import com.kin.counter.R;
import com.kin.counter.database.DatabaseHelper;
import com.kin.counter.userDao.Counter;

public class CounterItemActivity extends AppCompatActivity {
    private Counter mCounter;
    private int plusOrMinusOne;
    private MenuItem plusIncrement;
    private MenuItem minusIncrement;
    private TextView mCounterItemCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_item);

        Intent intent = getIntent();
        mCounter = (Counter) intent.getSerializableExtra("Counter");
        setTitle(mCounter.name);
        plusOrMinusOne = 1;

        mCounterItemCountTextView = (TextView) findViewById(R.id.counterItemCountTextView);
        mCounterItemCountTextView.setText(mCounter.count+"");
        mCounterItemCountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newVal = mCounter.count + plusOrMinusOne * mCounter.increment;
                if (newVal < 0) {
                    return;
                }
                else {
                    mCounterItemCountTextView.setText(newVal+"");
                    mCounter.count = newVal;
                    DatabaseHelper.getDatabaseHelper(getApplicationContext()).update(mCounter);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_counter_item, menu);
        plusIncrement = menu.findItem(R.id.plusIncrementActionItem);
        minusIncrement = menu.findItem(R.id.minusDecrementActionItem);
        if (plusOrMinusOne == 1) {
            plusIncrement.setVisible(true);
            minusIncrement.setVisible(false);
        }
        else {
            plusIncrement.setVisible(false);
            minusIncrement.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.plusIncrementActionItem:
                plusOrMinusOne = -1;
                plusIncrement.setVisible(false);
                minusIncrement.setVisible(true);
                break;

            case R.id.minusDecrementActionItem:
                plusOrMinusOne = 1;
                plusIncrement.setVisible(true);
                minusIncrement.setVisible(false);
                break;

            case R.id.editActionItem:
                createEditCounterAlertDialog().show();
                break;

            case R.id.deleteActionItem:
                createDeleteCounterAlertDialog().show();
                break;
        }
        return true;
    }

    private AlertDialog createEditCounterAlertDialog() {
        View alertLayout = getLayoutInflater().inflate(R.layout.alert_dialog_edit, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit");
        builder.setView(alertLayout);

        final EditText nameEditText = (EditText) alertLayout.findViewById(R.id.editItemNameEditText);
        final EditText countEditText = (EditText) alertLayout.findViewById(R.id.editItemCountEditText);
        final EditText IncrementEditText = (EditText) alertLayout.findViewById(R.id.editItemIncrementEditText);
        TextView confirmTextView = (TextView) alertLayout.findViewById(R.id.editConfirmDialogTextView);
        TextView cancelTextView = (TextView) alertLayout.findViewById(R.id.editCancelDialogTextView);

        nameEditText.setText(mCounter.name);
        nameEditText.setSelection(mCounter.name.length());
        countEditText.setText(mCounter.count + "");
        IncrementEditText.setText(mCounter.increment + "");
        final AlertDialog dialog = builder.create();

        confirmTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String countString = countEditText.getText().toString().trim();
                String incrementString = IncrementEditText.getText().toString().trim();

                if (CounterStringChecker.areStringsValid(CounterItemActivity.this, name, countString, incrementString)) {
                    mCounter.name = name;
                    mCounter.count = Integer.parseInt(countString);
                    mCounter.increment = Integer.parseInt(incrementString);
                    dialog.dismiss();
                    setTitle(name);
                    mCounterItemCountTextView.setText(countString);
                    DatabaseHelper.getDatabaseHelper(getApplicationContext()).update(mCounter);
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

    private AlertDialog createDeleteCounterAlertDialog() {
        View alertLayout = getLayoutInflater().inflate(R.layout.alert_dialog_delete, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion");
        builder.setView(alertLayout);

        TextView confirmTextView = (TextView) alertLayout.findViewById(R.id.deleteConfirmDialogTextView);
        TextView cancelTextView = (TextView) alertLayout.findViewById(R.id.deleteCancelDialogTextView);

        final AlertDialog dialog = builder.create();

        confirmTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                DatabaseHelper.getDatabaseHelper(getApplicationContext()).delete(mCounter);
                finish();
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
