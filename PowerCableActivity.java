package com.example.android.tsi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;

public class PowerCableActivity extends AppCompatActivity {
    @BindView(R.id.sp_source_voltage) Spinner sp_source_voltage;
    @BindView(R.id.et_voltage) EditText et_voltage;
    @BindView(R.id.et_amperage) EditText et_amperage;
    @BindView(R.id.et_wattage)EditText et_wattage;
    @BindView(R.id.et_distance) EditText et_distance;
    @BindView(R.id.tv_small_distance) TextView tv_small_distance;
    @BindView(R.id.tv_medium_distance) TextView tv_medium_distance;
    @BindView(R.id.tv_large_distance)TextView tv_large_distance;
    @BindView(R.id.tv_wire_gauge_result)TextView tv_wire_gauge_result;
    @BindView(R.id.tv_wire_distance_result) TextView tv_wire_distance_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_cable);
        View viewReq = findViewById(R.id.lo_top_right);
        View viewResults = findViewById(R.id.lo_bottom_right);
    }
}
