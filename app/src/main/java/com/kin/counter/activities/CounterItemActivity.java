package com.kin.counter.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kin.counter.R;
import com.kin.counter.database.DatabaseHelper;
import com.kin.counter.userDao.Counter;

public class CounterItemActivity extends AppCompatActivity {
    private Counter mCounter;
    private int plusOrMinusOne;
    private MenuItem plusStep;
    private MenuItem minusStep;
    private TextView mCounterItemCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_item);

        Intent intent = getIntent();
        mCounter = (Counter) intent.getSerializableExtra("Counter");
        setTitle(mCounter.name);
        Log.d("TAG", "ID: " + mCounter.id + " Name " + mCounter.name + " Count: " + mCounter.count + " Step: " + mCounter.step);
        plusOrMinusOne = 1;

        mCounterItemCountTextView = (TextView) findViewById(R.id.counterItemCountTextView);
        mCounterItemCountTextView.setText(mCounter.count+"");
        mCounterItemCountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newVal = mCounter.count + plusOrMinusOne * mCounter.step;
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
        plusStep = menu.findItem(R.id.plusStepActionItem);
        minusStep = menu.findItem(R.id.minusStepActionItem);
        if (plusOrMinusOne == 1) {
            plusStep.setVisible(true);
            minusStep.setVisible(false);
        }
        else {
            plusStep.setVisible(false);
            minusStep.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.plusStepActionItem:
                plusOrMinusOne = -1;
                plusStep.setVisible(false);
                minusStep.setVisible(true);
                break;

            case R.id.minusStepActionItem:
                plusOrMinusOne = 1;
                plusStep.setVisible(true);
                minusStep.setVisible(false);
                break;

            case R.id.editActionItem:
                break;

            case R.id.deleteActionItem:
                createDeleteCounterAlertDialog().show();
                break;
        }
        return true;
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
