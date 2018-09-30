package com.example.android.tsi;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.tsi.SqliteSum.SumDbHelper;
import com.example.android.tsi.SqliteSum.SumTaskContract;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.android.tsi.SqliteSum.SumTaskContract.SummaryEntry;
import com.example.android.tsi.Widget.SummaryService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class PowerLoadsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    //TODO add preference fragment for source voltage
    //TODO add preference for pf value
    private TextView tv_line_total_1, tv_line_total_2,tv_total_power_result, tv_total_pdu_result, tv_total_ups_result,tv_breaker_result;
    private EditText et_qty_1, et_watt_1, et_qty_2, et_watt_2;
    private Button btn_calculate;
    private int voltage = 120;
    private SQLiteDatabase mDb;
    private boolean imperial = true;
    @BindView(R.id.adViewBanner) AdView adViewBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_loads);
        ButterKnife.bind(this);
        View viewLoads = findViewById(R.id.lo_top_left);
        View viewResults = findViewById(R.id.lo_bottom_right);
        MobileAds.initialize(this, "ca-app-pub-8686454969066832~6147856904");
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBanner.loadAd(adRequest);
        setUpPreferences();
        et_qty_1 = viewLoads.findViewById(R.id.et_qty_1);
        et_watt_1 = viewLoads.findViewById(R.id.et_watt_1);
        tv_line_total_1 = viewLoads.findViewById(R.id.tv_line_total_1);
        et_qty_2 = viewLoads.findViewById(R.id.et_qty_2);
        et_watt_2 = viewLoads.findViewById(R.id.et_watt_2);
        tv_line_total_2 = viewLoads.findViewById(R.id.tv_line_total_2);
        tv_total_power_result = viewResults.findViewById(R.id.tv_total_power_result);
        tv_total_pdu_result = viewResults.findViewById(R.id.tv_total_pdu_result);
        tv_total_ups_result = viewResults.findViewById(R.id.tv_total_ups_result);
        tv_breaker_result = viewResults.findViewById(R.id.tv_breaker_result);
        btn_calculate = viewResults.findViewById(R.id.btn_calculate);
        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_qty_1.getText().toString()));    int qty1 = Integer.parseInt(et_qty_1.getText().toString());
                if (TextUtils.isEmpty(et_watt_1.getText().toString()));    int watt1 = Integer.parseInt(et_watt_1.getText().toString());
                int qty2 = Integer.parseInt(et_qty_2.getText().toString());
                int watt2 = Integer.parseInt(et_watt_2.getText().toString());
                int line1 = qty1 *watt1;
                tv_line_total_1.setText(line1+"");
                int line2 = qty2 *watt2;
                tv_line_total_2.setText(line2+"");
                int powerTotal = line1+line2;
                tv_total_power_result.setText(powerTotal+"W");
                double pduSize = powerTotal/.8;
                tv_total_pdu_result.setText(pduSize+"W");
                double upsSize = powerTotal/0.9;
                if(upsSize>(int)upsSize){
                    Log.d("Widget PwrLoads", upsSize+"");
                    upsSize+=1;    //round up the the next int
                    Log.d("Widget PwrLoads", upsSize+"");
                }
                tv_total_ups_result.setText((int)upsSize+"VA");
                int breakerSize = breakerSize(powerTotal);
                tv_breaker_result.setText(breakerSize+"A");

                SumDbHelper dbHelper = new SumDbHelper(getApplicationContext());
                mDb = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(SumTaskContract.SummaryEntry.COLUMN_SYSTEM, "Power Loads");
                contentValues.put(SumTaskContract.SummaryEntry.COLUMN_SUMMARY, "UPS Size "+(int)upsSize+"W");
                Cursor cursor = mDb.query(SummaryEntry.TABLE_NAME, null, null,null,null,null,null);
                if(cursor.moveToFirst()){
                    mDb.update(SummaryEntry.TABLE_NAME, contentValues,null, null);
                    Log.d("Widget PwrLoads", "update");
                }else {
                    long insert  =mDb.insert(SummaryEntry.TABLE_NAME, null, contentValues);
                    Log.d("Widget PwrLoads", "insert");
                }
                SummaryService.startActionUpdateSum(getApplicationContext());
            }
        });
    }
    private int breakerSize(double power){
        double current = power;
        double breakerSize = current/0.8;
        if(breakerSize<1) return 1;
        if(breakerSize<2) return 2;
        if(breakerSize<5) return 5;
        if(breakerSize<10) return 10;
        if(breakerSize<15) return 15;
        if(breakerSize<20) return 20;
        if(breakerSize<25) return 25;
        if(breakerSize<30) return 30;
        if(breakerSize<40) return 40;
        if(breakerSize<50) return 50;
        return 0;
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
            startActivity(new Intent(PowerLoadsActivity.this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
