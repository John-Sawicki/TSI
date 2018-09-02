package com.example.android.tsi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PowerLoadsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_loads);
        View viewLoads = findViewById(R.id.lo_top_left);
        View viewResults = findViewById(R.id.lo_bottom_right);
    }
}
