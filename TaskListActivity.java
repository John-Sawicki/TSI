package com.example.android.tsi;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tsi.Sqlite.TaskDbHelper;
import com.example.android.tsi.SqliteSum.SumDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.android.tsi.Sqlite.TaskContract.TaskEntry;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.example.android.tsi.Sqlite.TaskContract.TaskEntry;
import com.example.android.tsi.utilities.LocationClass;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class TaskListActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    @BindView(R.id.et_task_entry) EditText et_task_entry;
    @BindView(R.id.sp_system_name)Spinner sp_system_name;
    @BindView(R.id.tv_completed_tasks) TextView tv_completed_tasks;
    @BindView(R.id.btn_email_report) Button btn_email_report;
    @BindView(R.id.btn_add_task)Button btn_add_task;
    @BindView(R.id.adViewBanner) AdView adViewBanner;
    ArrayAdapter aa_spinner_system;
    private SQLiteDatabase mDb;
    private boolean imperial = true;
    private String systemName,location="TBD", systemSummary ="did stuff today";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        ButterKnife.bind(this);
        MobileAds.initialize(this, "ca-app-pub-8686454969066832~6147856904");
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBanner.loadAd(adRequest);
        setUpPreferences();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.system_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_system_name.setAdapter(adapter);
        sp_system_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //i is the value of the array index for the systems
                systemName = determineSystem(i);    //used to search a column in the db

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_email_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDbHelper dbHelper = new TaskDbHelper(getApplicationContext());
                mDb = dbHelper.getReadableDatabase();
                Cursor cctvCursor = mDb.query(TaskEntry.TABLE_NAME, null,"CCTV", null,null,null,null);
                if(cctvCursor.moveToFirst()){
                    String cctvSummary="";
                    for(int i = 0; i<cctvCursor.getCount(); i++){
                        cctvCursor.moveToPosition(i);
                        String cctvTask = cctvCursor.getString(cctvCursor.getColumnIndex(TaskEntry.DESCRIPTION));
                        Log.d("task query", cctvTask);
                        cctvSummary +=cctvTask;
                    }
                }
                Cursor lanCursor = mDb.query(TaskEntry.TABLE_NAME, null,"LAN", null,null,null,null);
                if(cctvCursor.moveToFirst()){
                    String lanSummary="";
                    for(int i = 0; i<lanCursor.getCount(); i++){
                        lanCursor.moveToPosition(i);
                        String lanTask = lanCursor.getString(lanCursor.getColumnIndex(TaskEntry.DESCRIPTION));
                        Log.d("task query", lanTask);
                        lanSummary +=lanTask;
                    }
                }
            }
        });
        btn_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Added "+systemName+" task to list.", Toast.LENGTH_SHORT).show();
                systemSummary = et_task_entry.getText().toString();
                TaskDbHelper dbHelper = new TaskDbHelper(getApplicationContext());
                mDb = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                String todaysDate = sdf.format(new Date());
                Log.d("task date", todaysDate);
                contentValues.put(TaskEntry.DATE, todaysDate);
                contentValues.put(TaskEntry.SYSTEM, systemName);
                Log.d("task summary", systemSummary);
                contentValues.put(TaskEntry.DESCRIPTION, systemSummary);
                LocationClass locationClass = new LocationClass();
                location = locationClass.getLocation(getApplicationContext());
                Log.d("task location", location);
                contentValues.put(TaskEntry.LOCATION, location);
                contentValues.put(TaskEntry.STATUS, "Complete");
            }
        });
    }

    private String determineSystem(int systemIndex){
        switch(systemIndex){
            case 0: return "CCTV";
            case 1: return "Cabinets";
            case 2: return "ETV";
            case 3: return "LAN";
            case 4: return "PAGA";
            case 5: return "Radar";
            case 6: return "Radio";
            case 7: return "SCS";
            case 8: return "UPS";
            default: return "Misc.";
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
            startActivity(new Intent(TaskListActivity.this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
