package com.example.android.tsi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.tsi.SqliteSum.SumDbHelper;
import com.example.android.tsi.SqliteSum.SumTaskContract;
import com.example.android.tsi.Widget.SummaryService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;

public class PowerCableActivity extends AppCompatActivity {
    /*
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
    */
    protected Spinner sp_source_voltage;
    protected EditText et_voltage, et_amperage, et_wattage, et_distance, et_percent_drop;
    protected TextView tv_small_wire, tv_small_distance, tv_medium_wire, tv_medium_distance, tv_large_wire,tv_large_distance,
    tv_wire_gauge_result, tv_wire_distance_result;
    protected ImageView iv_small_wire, iv_medium_wire, iv_large_wire;
    protected Button btn_calculate;
    private SQLiteDatabase mDb;
    private int voltage;
    @BindView(R.id.adViewBanner) AdView adViewBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_cable);
        View viewReq = findViewById(R.id.lo_top_right);
        View viewResults = findViewById(R.id.lo_bottom_right);
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBanner.loadAd(adRequest);
        et_voltage =viewReq.findViewById(R.id.et_voltage);
       // voltage=et_voltage.toString()
        et_amperage = viewReq.findViewById(R.id.et_amperage);
        et_wattage = viewReq.findViewById(R.id.et_wattage);
        et_distance = viewReq.findViewById(R.id.et_distance);
        et_percent_drop = viewReq.findViewById(R.id.et_percent_drop);
        tv_small_wire = viewResults.findViewById(R.id.tv_small_wire);
        tv_small_distance = viewResults.findViewById(R.id.tv_small_distance);
        tv_medium_wire = viewResults.findViewById(R.id.tv_medium_wire);
        tv_medium_distance = viewResults.findViewById(R.id.tv_medium_distance);
        tv_large_wire = viewResults.findViewById(R.id.tv_large_wire);
        tv_large_distance = viewResults.findViewById(R.id.tv_large_distance);
        tv_wire_gauge_result = viewResults.findViewById(R.id.tv_wire_gauge_result);
        tv_wire_distance_result = viewResults.findViewById(R.id.tv_wire_distance_result);
        iv_small_wire= viewResults.findViewById(R.id.iv_small_wire);
        iv_medium_wire= viewResults.findViewById(R.id.iv_medium_wire);
        iv_large_wire= viewResults.findViewById(R.id.iv_large_wire);
        sp_source_voltage = viewReq.findViewById(R.id.sp_source_voltage);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.voltages,android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_source_voltage.setAdapter(adapter);
        sp_source_voltage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                voltage = determineVoltage(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_calculate = viewResults.findViewById(R.id.btn_calculate);
        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SumDbHelper dbHelper = new SumDbHelper(getApplicationContext());
                mDb = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(SumTaskContract.SummaryEntry.COLUMN_SYSTEM, "Power Cable");
                contentValues.put(SumTaskContract.SummaryEntry.COLUMN_SUMMARY, "Max cable distance");
                Cursor cursor = mDb.query(SumTaskContract.SummaryEntry.TABLE_NAME, null, null,null,null,null,null);
                if(cursor.moveToFirst()){
                    mDb.update(SumTaskContract.SummaryEntry.TABLE_NAME, contentValues,null, null);
                    Log.d("Widget PwrLoads", "update");
                }else {
                    long insert  =mDb.insert(SumTaskContract.SummaryEntry.TABLE_NAME, null, contentValues);
                    Log.d("Widget PwrLoads", "insert");
                }
                SummaryService.startActionUpdateSum(getApplicationContext());
            /*
                double totalVdrop = vSource*percentDrop;
                double cableDrop = totalVdrop- (vSource - vParent);
                double current = wPower/ vParent;
                double rMax = cableDrop/current;
                if( (distance/1000)*1.588< rMax) return 12;
                if( (distance/1000)*0.9989< rMax) return 10;
                if( (distance/1000)*0.6282< rMax) return 8;
                if( (distance/1000)*0.3951< rMax) return 6;
                if( (distance/1000)*0.2485< rMax) return 4;
                if( (distance/1000)*0.1563< rMax) return 2;

                if(gauge ==12){
                    (rMax/0.9989)*1000 = distance1;	//10AWG
                    (rMax/0.0.6282)*1000 = distance2;//8AWG

                }
            */
            }
        });

    }
    private int determineVoltage(int voltageIndex){
        switch(voltageIndex){
            case 0: return 12;
            case 1: return 24;
            case 2: return 48;
            case 3: return 120;
            case 4: return 220;
            default: return 120;
        }
    }
}
