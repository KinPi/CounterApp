package com.kin.counter.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kin.counter.R;
import com.kin.counter.database.DatabaseHelper;
import com.kin.counter.userDao.Counter;

public class NewCounterItemActivity extends AppCompatActivity {
    private EditText newCounterItemNameEditText;
    private EditText newCounterItemCountEditText;
    private EditText newCounterItemIncrementEditText;
    private Button newCounterItemSaveButton;
    private Button newCounterItemCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_counter_item);

        newCounterItemNameEditText = (EditText) findViewById(R.id.newCounterItemNameTextEdit);
        newCounterItemCountEditText = (EditText) findViewById(R.id.newCounterItemCountTextEdit);
        newCounterItemIncrementEditText = (EditText) findViewById(R.id.newCounterItemIncrementEditText);
        newCounterItemSaveButton = (Button) findViewById(R.id.newCounterItemSaveButton);
        newCounterItemCancelButton = (Button) findViewById(R.id.newCounterItemCancelButton);

        newCounterItemSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = newCounterItemNameEditText.getText().toString().trim();
                String countString = newCounterItemCountEditText.getText().toString().trim();
                String incrementString = newCounterItemIncrementEditText.getText().toString().trim();

                if (name.length() == 0 || name.length() > 15) {
                    Toast.makeText(NewCounterItemActivity.this, "Please enter a name that's between 1 to 15 characters!", Toast.LENGTH_SHORT).show();
                }

                else if (countString.length() == 0) {
                    Toast.makeText(NewCounterItemActivity.this, "Please enter a count value!", Toast.LENGTH_SHORT).show();
                }

                else if (incrementString.length() == 0) {
                    Toast.makeText(NewCounterItemActivity.this, "Please enter a increment value!", Toast.LENGTH_SHORT).show();
                }

                else if (Integer.parseInt(incrementString) <= 0) {
                    Toast.makeText(NewCounterItemActivity.this, "Please enter a positive integer for increment!", Toast.LENGTH_SHORT).show();
                }

                else {
                    int count = Integer.parseInt(countString.trim());
                    int increment = Integer.parseInt(incrementString);

                    Counter counter = new Counter(name, count, increment);
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
