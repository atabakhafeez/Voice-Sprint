package com.voicesprint.variable_j.voicesprint;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by atabakh on 17/04/2016.
 */
public class Quit extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }
    }
}
