package com.kin.counter;

import android.content.Context;
import android.widget.Toast;


public class CounterStringChecker {

    public static boolean areStringsValid(Context context, String name, String countString, String incrementString) {
        if (name.length() == 0 || name.length() > 15) {
            Toast.makeText(context, "Please enter a name that's between 1 to 15 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if (countString.length() == 0) {
            Toast.makeText(context, "Please enter a count value!", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if (incrementString.length() == 0) {
            Toast.makeText(context, "Please enter a increment value!", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if (Integer.parseInt(incrementString) <= 0) {
            Toast.makeText(context, "Please enter a positive integer for increment!", Toast.LENGTH_SHORT).show();
            return false;
        }

        else {
            return true;
        }
    }
}
