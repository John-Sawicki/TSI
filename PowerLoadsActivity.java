package com.example.android.tsi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.tsi.SqliteSum.SumDbHelper;
import com.example.android.tsi.SqliteSum.SumTaskContract;

import butterknife.BindView;
import com.example.android.tsi.SqliteSum.SumTaskContract.SummaryEntry;
import com.example.android.tsi.Widget.SummaryService;

public class PowerLoadsActivity extends AppCompatActivity {
    //TODO add preference fragment for source voltage
    //TODO add preference for pf value
    private TextView tv_line_total_1, tv_line_total_2,tv_total_power_result, tv_total_pdu_result, tv_total_ups_result,tv_breaker_result;
    private EditText et_qty_1, et_watt_1, et_qty_2, et_watt_2;
    private Button btn_calculate;
    private int voltage = 120;
    private SQLiteDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_loads);
        View viewLoads = findViewById(R.id.lo_top_left);
        View viewResults = findViewById(R.id.lo_bottom_right);
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
        return 0;
    }
}
