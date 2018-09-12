package com.example.android.tsi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

public class PagaActivity extends AppCompatActivity {

    FrameLayout mFrameBackground;
    ImageView ivCircle0, ivCircle1, ivCircle2, ivCircle3, ivCircle4, ivCircle5;
    TextView tv_db_level1, tv_distance1, tv_db_level2, tv_distance2, tv_db_level3, tv_distance3, tv_db_level4, tv_distance4, tv_db_level5, tv_distance5, tv_db_level6, tv_distance6;
    Button btn_calculate;
    EditText et_speaker_output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paga);
        View viewCircle = findViewById(R.id.lo_top_right);
        View viewDb = findViewById(R.id.lo_bottom_left);
        mFrameBackground = viewCircle.findViewById(R.id.im_background);
        ivCircle0 = viewCircle.findViewById(R.id.im_circle0);
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
        et_speaker_output = viewDb.findViewById(R.id.et_speaker_output);
        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO change background color
                int power = Integer.parseInt(et_speaker_output.getText().toString());
                if(power>=200 ){
                    ivCircle5.setColorFilter(getResources().getColor(R.color.circle_9_DarkRed));
                    ivCircle4.setColorFilter(getResources().getColor(R.color.circle_8_Red));
                    ivCircle3.setColorFilter(getResources().getColor(R.color.circle_7_Orange));
                    ivCircle2.setColorFilter(getResources().getColor(R.color.circle_6_LightOrange));
                    ivCircle1.setColorFilter(getResources().getColor(R.color.circle_5_Yellow));
                    ivCircle0.setColorFilter(getResources().getColor(R.color.circle_4_LightGreen));
                    mFrameBackground.setBackgroundColor(getResources().getColor(R.color.circle_1_Blue));
                }
                if(power>=50&&power<200){
                    ivCircle5.setColorFilter(getResources().getColor(R.color.circle_8_Red));
                    ivCircle4.setColorFilter(getResources().getColor(R.color.circle_7_Orange));
                    ivCircle3.setColorFilter(getResources().getColor(R.color.circle_6_LightOrange));
                    ivCircle2.setColorFilter(getResources().getColor(R.color.circle_5_Yellow));
                    ivCircle1.setColorFilter(getResources().getColor(R.color.circle_4_LightGreen));
                    ivCircle0.setColorFilter(getResources().getColor(R.color.circle_3_Turquoise));
                    mFrameBackground.setBackgroundColor(getResources().getColor(R.color.circle_1_Blue));
                }
                if(power>=30&&power<50){
                    ivCircle5.setColorFilter(getResources().getColor(R.color.circle_7_Orange));
                    ivCircle4.setColorFilter(getResources().getColor(R.color.circle_6_LightOrange));
                    ivCircle3.setColorFilter(getResources().getColor(R.color.circle_5_Yellow));
                    ivCircle2.setColorFilter(getResources().getColor(R.color.circle_4_LightGreen));
                    ivCircle1.setColorFilter(getResources().getColor(R.color.circle_3_Turquoise));
                    ivCircle0.setColorFilter(getResources().getColor(R.color.circle_2_LightBlue));
                    mFrameBackground.setBackgroundColor(getResources().getColor(R.color.circle_1_Blue));
                }
                if(power>=15&&power<30){
                    ivCircle5.setColorFilter(getResources().getColor(R.color.circle_6_LightOrange));
                    ivCircle4.setColorFilter(getResources().getColor(R.color.circle_5_Yellow));
                    ivCircle3.setColorFilter(getResources().getColor(R.color.circle_4_LightGreen));
                    ivCircle2.setColorFilter(getResources().getColor(R.color.circle_3_Turquoise));
                    ivCircle1.setColorFilter(getResources().getColor(R.color.circle_2_LightBlue));
                    ivCircle0.setColorFilter(getResources().getColor(R.color.circle_1_Blue));
                    mFrameBackground.setBackgroundColor(getResources().getColor(R.color.circle_1_Blue));
                }
                if(power>0&&power<15){
                    ivCircle5.setColorFilter(getResources().getColor(R.color.circle_5_Yellow));
                    ivCircle4.setColorFilter(getResources().getColor(R.color.circle_4_LightGreen));
                    ivCircle3.setColorFilter(getResources().getColor(R.color.circle_3_Turquoise));
                    ivCircle2.setColorFilter(getResources().getColor(R.color.circle_2_LightBlue));
                    ivCircle1.setColorFilter(getResources().getColor(R.color.circle_1_Blue));
                    ivCircle0.setColorFilter(getResources().getColor(R.color.circle_1_Blue));
                    mFrameBackground.setBackgroundColor(getResources().getColor(R.color.circle_1_Blue));
                }

            }

        });
    }
}
