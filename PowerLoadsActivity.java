package com.example.android.tsi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;

public class PowerLoadsActivity extends AppCompatActivity {
    //TODO add preference fragment for source voltage
    //TODO add preference for pf value
    private TextView tv_line_total_1, tv_line_total_2,tv_total_power_result, tv_total_pdu_result, tv_total_ups_result,tv_breaker_result;
    private EditText et_qty_1, et_watt_1, et_qty_2, et_watt_2;
    private Button btn_calculate;
    private int voltage = 120;
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
                int qty1 = Integer.parseInt(et_qty_1.getText().toString());
                int watt1 = Integer.parseInt(et_watt_1.getText().toString());
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
                tv_total_ups_result.setText(upsSize+"VA");
                int breakerSize = breakerSize(powerTotal);
                tv_breaker_result.setText(breakerSize+"A");
            }
        });
    }
    private int breakerSize(double power){
        return 0;
    }
}
