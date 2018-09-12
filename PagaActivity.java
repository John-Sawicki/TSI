package com.example.android.tsi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

public class PagaActivity extends AppCompatActivity {

    FrameLayout mFrameBackground;
    ImageView ivCircle1, ivCircle2, ivCircle3, ivCircle4, ivCircle5;
    TextView tv_db_level1, tv_distance1, tv_db_level2, tv_distance2, tv_db_level3, tv_distance3, tv_db_level4, tv_distance4, tv_db_level5, tv_distance5, tv_db_level6, tv_distance6;
    Button btn_calculate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paga);
        View viewCircle = findViewById(R.id.lo_top_right);
        View viewDb = findViewById(R.id.lo_bottom_left);
        mFrameBackground = viewCircle.findViewById(R.id.im_background);
        ivCircle1= viewCircle.findViewById(R.id.im_circle1);
        ivCircle2= viewCircle.findViewById(R.id.im_circle2);
        ivCircle3= viewCircle.findViewById(R.id.im_circle3);
        ivCircle4= viewCircle.findViewById(R.id.im_circle4);
        ivCircle5= viewCircle.findViewById(R.id.im_circle5);
        tv_db_level1 = viewDb.findViewById(R.id.tv_db_level1);
        tv_distance1 = viewDb.findViewById(R.id.tv_distance1);
        tv_db_level1 = viewDb.findViewById(R.id.tv_db_level2);
        tv_distance1 = viewDb.findViewById(R.id.tv_distance2);
        tv_db_level1 = viewDb.findViewById(R.id.tv_db_level3);
        tv_distance1 = viewDb.findViewById(R.id.tv_distance3);
        tv_db_level1 = viewDb.findViewById(R.id.tv_db_level4);
        tv_distance1 = viewDb.findViewById(R.id.tv_distance4);
        tv_db_level1 = viewDb.findViewById(R.id.tv_db_level5);
        tv_distance1 = viewDb.findViewById(R.id.tv_distance5);
        tv_db_level1 = viewDb.findViewById(R.id.tv_db_level6);
        tv_distance1 = viewDb.findViewById(R.id.tv_distance6);
        btn_calculate = viewDb.findViewById(R.id.btn_calculate);
        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO change background color
                //ivCircle5.setBackgroundColor(getResources().getColor(R.color.circle_8_Red));
                ivCircle5.setColorFilter(getResources().getColor(R.color.circle_8_Red));

            }

        });
    }
}
