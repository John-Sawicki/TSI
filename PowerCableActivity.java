package com.example.android.tsi;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.android.tsi.utilities.WireGauageCalc;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PowerCableActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
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
    private double doubleZgauge = 0.078, zeroGauage = 0.0983, twoGauge = 0.1563, fourGuge = 0.2485, sixGauge = 0.3951, eightGauge = 0.6282,
            tenGauge = 0.9989,twelveGauge = 1.588, fourteenGauge = 2.525, sixteenGgauge = 4.016,eighteenGauge = 6.385;
    private int  vParent = 120 ;
    private double percentDrop = 2.5, totalVdrop, parentVdrop, childVdrop, current, resistanceMax, power = 200.0, distance = 0.0;
    @BindView(R.id.adViewBanner) AdView adViewBanner;
    private boolean imperial = true;
    String wireGauge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_cable);
        ButterKnife.bind(this);
        View viewReq = findViewById(R.id.lo_top_left);
        View viewResults = findViewById(R.id.lo_bottom_right);
        MobileAds.initialize(this, "ca-app-pub-8686454969066832~6147856904");
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBanner.loadAd(adRequest);
        setUpPreferences();
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
                Log.d("power cable","onClick");
                vParent= Integer.parseInt(  et_voltage.getText().toString()  ); Log.d("power cable","vParent "+vParent);
                percentDrop = Double.parseDouble( et_percent_drop.getText().toString() );Log.d("power cable","vDrop "+percentDrop);
                power = Double.parseDouble(  et_wattage.getText().toString() );Log.d("power cable","power "+power);
                distance = Double.parseDouble(    et_distance.getText().toString());Log.d("power cable","distance "+distance);
                totalVdrop = voltage*(percentDrop/100);Log.d("power cable","totalVdrop "+totalVdrop);//max voltage drop from the source voltage
                parentVdrop = voltage - vParent;   Log.d("power cable","parentVdrop "+parentVdrop); //how much the voltage has drop to the downstream point being calculated
                childVdrop = totalVdrop - parentVdrop; Log.d("power cable","childVdrop "+childVdrop); //how much the the voltage can drop for the given wire
                current = power/ vParent;Log.d("power cable","current "+current);
                resistanceMax =childVdrop/(power/vParent);Log.d("power cable","resistanceMax "+resistanceMax);  //maximum wire resistance to get the required voltage drop value
                Log.d("power cable","distance "+distance+" rmax "+resistanceMax+" imperial "+imperial );
                WireGauageCalc wireGauageCalc = new WireGauageCalc();
                wireGauge = wireGauageCalc.calculateWireGauge(distance, resistanceMax, imperial);
                Log.d("power cable",wireGauge );
                SumDbHelper dbHelper = new SumDbHelper(getApplicationContext());
                mDb = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(SumTaskContract.SummaryEntry.COLUMN_SYSTEM, "Power Cable\nmin. Size");
                contentValues.put(SumTaskContract.SummaryEntry.COLUMN_SUMMARY, wireGauge);
                Cursor cursor = mDb.query(SumTaskContract.SummaryEntry.TABLE_NAME, null, null,null,null,null,null);
                if(cursor.moveToFirst()){
                    mDb.update(SumTaskContract.SummaryEntry.TABLE_NAME, contentValues,null, null);
                    Log.d("Widget PwrLoads", "update");
                }else {
                    long insert  =mDb.insert(SumTaskContract.SummaryEntry.TABLE_NAME, null, contentValues);
                    Log.d("Widget PwrLoads", "insert");
                }
                SummaryService.startActionUpdateSum(getApplicationContext());

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
    private void setUpPreferences() {//sets up preferences when the user reopens the activity
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.registerOnSharedPreferenceChangeListener(this);
        imperial = sharedPref.getBoolean(getResources().getString(R.string.pref_units_key), true);
        Log.d("pref fragment", imperial+" setup");
    }
    @Override//updates the activity once the units system has changed
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getResources().getString(R.string.pref_units_key))){
            imperial = sharedPreferences.getBoolean(getResources().getString(R.string.pref_units_key), true);
            Log.d("pref fragment", imperial+" changed");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if(itemClicked==R.id.shared_pref){
            Log.d("menu", "menu clicked");
            startActivity(new Intent(PowerCableActivity.this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
