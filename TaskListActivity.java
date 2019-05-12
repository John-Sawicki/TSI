package com.john.android.tsi;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.john.android.tsi.Room.AppDatabase;
import com.john.android.tsi.Room.TaskEntryRm;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.john.android.tsi.SqliteSum.SumDbHelper;
import com.john.android.tsi.SqliteSum.SumTaskContract;
import com.john.android.tsi.Widget.SummaryService;
import com.john.android.tsi.utilities.ApiKey;
import com.john.android.tsi.utilities.LocationAsyncTask;
import com.john.android.tsi.utilities.LocationClass;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import android.Manifest;
import android.widget.Toast;
import static android.view.inputmethod.EditorInfo.IME_MASK_ACTION;

public class TaskListActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener, LocationAsyncTask.OnAddressComplete {
    @BindView(R.id.et_task_entry) EditText et_task_entry;
    @BindView(R.id.sp_system_name)Spinner sp_system_name;
    @BindView(R.id.tv_completed_tasks) TextView tv_completed_tasks;
    @BindView(R.id.btn_email_report) Button btn_email_report;
    @BindView(R.id.btn_add_task)Button btn_add_task;
    @BindView(R.id.adViewBanner) AdView adViewBanner;
    private SQLiteDatabase mDb;
    private boolean imperial = true, asyncDone = false, validEmail, locationUpdated = false, gpsPermission = false;
    private String locationString="TBD", systemSummary ="did stuff today", systemName, emailSummary="", emailAddress, urlBase, taskSummary="\n";
    private int systemInt=0;//values reference position in spinner for the system
    private static String ACTIVITY = "TASK_LIST_ACT";
    private AppDatabase appDb;//used for room
    LocationManager locationManager;
    LocationListener locationListener;
    private double[] latLong={0,0};
    List<TaskEntryRm> completedTasks;
    private void updateLocation(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            Location onCreateLocation =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latLong[0] = onCreateLocation.getLatitude();
            latLong[1] = onCreateLocation.getLongitude();
            Log.d(ACTIVITY, "updateLocation latLong "+latLong[0]+" "+latLong[1]);
        }
        urlBase= "https://maps.googleapis.com/maps/api/geocode/json?latlng="+ latLong[0]+","+ latLong[1]+"&sensor=true&key="+ApiKey.GoogleApiKey;
        LocationAsyncTask task = new LocationAsyncTask(TaskListActivity.this);//method b using a separate file
        task.execute(urlBase);//value updated by the interface
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            latLong[0]= savedInstanceState.getDouble("latLong_zero",0.0);
            latLong[1]= savedInstanceState.getDouble("latLong_one",0.0);
            taskSummary = savedInstanceState.getString("taskSummary","");
            Log.d(ACTIVITY, "savedInstance latLong "+latLong[0]+" "+latLong[1]+" "+taskSummary);
        }
        setContentView(R.layout.activity_task_list);
        ButterKnife.bind(this);
        tv_completed_tasks.setText(taskSummary);
        MobileAds.initialize(this, "ca-app-pub-8686454969066832~6147856904");
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBanner.loadAd(adRequest);
        emailSummary="No tasks have been entered.";//rest to empty so when the user presses back it doesnt keep adding to the string
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        updateLocation();
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i(ACTIVITY, "onLocationChanged method "+location.toString());
                latLong[0] = location.getLatitude();
                latLong[1] = location.getLongitude();
                Log.i(ACTIVITY, "onLocationChanged lat and lon "+Double.toString(latLong[0])+" "+Double.toString(latLong[1]));
                LocationAsyncTask task = new LocationAsyncTask(TaskListActivity.this);//method b using a separate file
                urlBase= "https://maps.googleapis.com/maps/api/geocode/json?latlng="+ latLong[0]+","+ latLong[1]+"&sensor=true&key="+ApiKey.GoogleApiKey;
                task.execute(urlBase);//value updated by the interface
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }
            @Override
            public void onProviderEnabled(String s) {
            }
            @Override
            public void onProviderDisabled(String s) {
            }
        };
        et_task_entry.setImeOptions(IME_MASK_ACTION);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{//permission granted
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2*1000, 1, locationListener);
        }
        setUpPreferences();
        appDb = AppDatabase.getInstance(getApplicationContext());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.system_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_system_name.setAdapter(adapter);
        sp_system_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                systemName = LocationClass.determineSystem(i);  Log.d(ACTIVITY, "system name" +systemName);
                systemInt = i;//use spinner position for system name
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btn_email_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validEmail =TextUtils.isEmpty(emailAddress)||!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches(); //empty or invalid email address
                boolean isEmailEmpty =TextUtils.isEmpty(emailAddress);
                boolean emailMatcher =!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
                Log.d(ACTIVITY,"valid email? "+emailAddress+" "+isEmailEmpty+" "+emailMatcher);
                Context context = getApplicationContext();
                ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(validEmail){
                    Toast toast = Toast.makeText(context, R.string.toast_no_email_address, Toast.LENGTH_LONG);
                    toast.show();
                }else if(!isConnected) {
                    Toast toast = Toast.makeText(context, R.string.toast_no_network, Toast.LENGTH_LONG);
                    toast.show();
                }else if(emailSummary!=null ||emailSummary.equals("No tasks have been entered.")){
                    Toast toast = Toast.makeText(context, "No tasks have been entered.", Toast.LENGTH_LONG);
                    toast.show();
                } else{
                    retrieveTasks();
                    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd");//ex Tuesday May 5, 2018
                    String SummaryDateString = format.format(new Date());
                    Log.d(ACTIVITY, "email button address "+emailAddress+" date "+SummaryDateString);
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",emailAddress,null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "TSI Summary Report "+SummaryDateString);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, emailSummary);
                    Log.d(ACTIVITY, "email summary "+emailSummary);
                    startActivity(Intent.createChooser(emailIntent, "Send Email..."));
                    deleteAllTasks();
                    Log.d(ACTIVITY, "deleteAllTasks completed ");
                    taskSummary="\n";
                    tv_completed_tasks.setText(taskSummary);
                }
            }
        });
        btn_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd, yyyy");//ex Tuesday May 5, 2018
                String dateString = format.format(new Date());  Log.d(ACTIVITY, "today is "+dateString);
                systemSummary =et_task_entry.getText().toString();//text entered by the user
                taskSummary+=systemSummary+"\n";//have the bottom half of the screen only show the actions for the task
                Log.d(ACTIVITY, "task summary add button "+taskSummary);
                tv_completed_tasks.setText(taskSummary);
                SumDbHelper dbHelper = new SumDbHelper(getApplicationContext());
                mDb = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(SumTaskContract.SummaryEntry.COLUMN_SYSTEM, getString(com.john.android.tsi.R.string.widget_tracker_title));
                contentValues.put(SumTaskContract.SummaryEntry.COLUMN_SUMMARY, systemSummary);
                Cursor cursor = mDb.query(SumTaskContract.SummaryEntry.TABLE_NAME, null, null,null,null,null,null);
                if(cursor.moveToFirst()){
                    mDb.update(SumTaskContract.SummaryEntry.TABLE_NAME, contentValues,null, null);
                    Log.d("Widget PwrLoads", "update");//
                }else {
                    long insert  =mDb.insert(SumTaskContract.SummaryEntry.TABLE_NAME, null, contentValues);
                    Log.d("Widget PwrLoads", "insert");

                }cursor.close();
                SummaryService.startActionUpdateSum(getApplicationContext());
                Log.d(ACTIVITY, "room values "+dateString+" "+systemName+" "+systemSummary+" "+locationString);
                final TaskEntryRm taskToAdd  = new TaskEntryRm(dateString,systemName, systemSummary, locationString, 0);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(ACTIVITY, "add task, run method");
                        appDb.taskDao().insertTask(taskToAdd);
                    }
                });
                retrieveTasks();
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, R.string.task_saved, Toast.LENGTH_SHORT);
                toast.show();
                et_task_entry.setText("");//empties the text the user entered for the past task
            }
        });
    }
    @Override
    public void onAddressComplete(String address) {
        locationString = address;
        Log.d(ACTIVITY, "address update interface "+address+" "+locationString);
        if(!locationUpdated){//let the user know the first time that they go to the activity that a non-null address has been retrieved from Google
            locationUpdated = true;
            Toast toast = Toast.makeText(getApplicationContext(), R.string.task_updated, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private void retrieveTasks(){
        LiveData<List<TaskEntryRm>> tasks = appDb.taskDao().loadAllTasks();
        tasks.observe(this, new Observer<List<TaskEntryRm>>() {
            @Override
            public void onChanged(@Nullable List<TaskEntryRm> taskEntryRms) {
                if(taskEntryRms!=null){
                    emailSummary="No tasks have been entered.";//reset
                    completedTasks = taskEntryRms;//when a task is
                    for(int i= 0; i<taskEntryRms.size();i++){
                        TaskEntryRm taskEntry = taskEntryRms.get(i);
                        String systemDate = taskEntry.getDate();
                        String systemName = taskEntry.getSystem();
                        String systemSummary = taskEntry.getSummary();
                        String systemLocation = taskEntry.getLocation();
                        emailSummary+= systemName+"\n"+systemDate+"\n"+systemSummary+"\n"+systemLocation+"\n";
                    }
                }
            }
        });
    }
    private void deleteAllTasks(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i<completedTasks.size();i++){
                    appDb.taskDao().deleteTask(completedTasks.get(i));
                    Log.d(ACTIVITY, "deleted task "+i);
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Log.i(ACTIVITY, "onRequestPermissionsResult PERMISSION_GRANTED");
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        10000, 10, locationListener);//get location updates every minute
                Log.i(ACTIVITY, "onRequestPermissionsResult PERMISSION_GRANTED via manifest");
            }
        }
    }
    private void setUpPreferences() {//sets up preferences when the user reopens the activity
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.registerOnSharedPreferenceChangeListener(this);
        emailAddress= sharedPref.getString(getResources().getString(R.string.pref_email_key),"set up email address");
    }
    @Override//updates the activity once the units system has changed
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getResources().getString(R.string.pref_email_key))){
            emailAddress = sharedPreferences.getString(getResources().getString(R.string.pref_email_key),"set up email address");
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("latLong_zero", latLong[0]);
        outState.putDouble("latLong_one", latLong[1]);
        outState.putString("taskSummary",taskSummary);
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
        }else if (itemClicked==R.id.menu_go_to_main){
            startActivity(new Intent(TaskListActivity.this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }
    @Override
    public void onPause() {
        if (adViewBanner != null) adViewBanner.pause();
        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (adViewBanner != null) adViewBanner.resume();
    }
    @Override
    public void onDestroy() {
        if (adViewBanner != null) adViewBanner.destroy();
        super.onDestroy();
    }
}

