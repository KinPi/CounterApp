package com.kin.counter.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kin.counter.R;
import com.kin.counter.database.DatabaseHelper;
import com.kin.counter.userDao.Counter;

public class NewCounterItemActivity extends AppCompatActivity {
    private EditText newCounterItemNameEditText;
    private EditText newCounterItemCountEditText;
    private EditText newCounterItemStepEditText;
    private Button newCounterItemSaveButton;
    private Button newCounterItemCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_counter_item);

        newCounterItemNameEditText = (EditText) findViewById(R.id.newCounterItemNameTextEdit);
        newCounterItemCountEditText = (EditText) findViewById(R.id.newCounterItemCountTextEdit);
        newCounterItemStepEditText = (EditText) findViewById(R.id.newCounterItemStepEditText);
        newCounterItemSaveButton = (Button) findViewById(R.id.newCounterItemSaveButton);
        newCounterItemCancelButton = (Button) findViewById(R.id.newCounterItemCancelButton);

        newCounterItemSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newCounterItemNameEditText.getText().length() == 0 ||
                        newCounterItemCountEditText.getText().length() == 0 ||
                        newCounterItemStepEditText.getText().length() == 0 ||
                        Integer.parseInt(newCounterItemStepEditText.getText().toString()) <= 0) {
                    return;
                }

                else {
                    String name = newCounterItemNameEditText.getText().toString();
                    int count = Integer.parseInt(newCounterItemCountEditText.getText().toString());
                    int step = Integer.parseInt(newCounterItemStepEditText.getText().toString());

                    Counter counter = new Counter(name, count, step);
                    DatabaseHelper db = DatabaseHelper.getDatabaseHelper(getApplicationContext());
                    long result = db.add(counter);
                    Intent intent = new Intent();
                    intent.putExtra("Id", result);

                    if (result >= 1) {
                        setResult(RESULT_OK, intent);
                    }
                    else {
                        setResult(RESULT_CANCELED);
                    }
                    finish();
                }
            }
        });

        newCounterItemCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
