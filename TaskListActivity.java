package com.example.android.tsi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.BindView;

public class TaskListActivity extends AppCompatActivity {
    @BindView(R.id.sp_system_name)Spinner sp_system_name;
    ArrayAdapter aa_spinner_system;
    private String systemName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
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
}
