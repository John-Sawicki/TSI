package com.example.android.tsi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagaActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    FrameLayout mFrameBackground;
    ImageView ivCircle0, ivCircle1, ivCircle2, ivCircle3, ivCircle4, ivCircle5, ivCircle6, ivCircle7, ivCircle8, ivCircle9;
    TextView tv_db_level1, tv_distance1, tv_db_level2, tv_distance2, tv_db_level3, tv_distance3, tv_db_level4, tv_distance4, tv_db_level5, tv_distance5, tv_db_level6, tv_distance6;
    Button btn_calculate;
    EditText et_speaker_output;
    @BindView(R.id.adViewBanner) AdView adViewBanner;
    private boolean imperial = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paga);
        ButterKnife.bind(this);
        View viewCircle = findViewById(R.id.lo_top_right);
        View viewDb = findViewById(R.id.lo_bottom_left);
        MobileAds.initialize(this, "ca-app-pub-8686454969066832~6147856904");
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBanner.loadAd(adRequest);
        setUpPreferences();
        mFrameBackground = viewCircle.findViewById(R.id.im_background);
        ivCircle0 = viewCircle.findViewById(R.id.im_circle0);
        ivCircle1= viewCircle.findViewById(R.id.im_circle1);
        ivCircle2= viewCircle.findViewById(R.id.im_circle2);
        ivCircle3= viewCircle.findViewById(R.id.im_circle3);
        ivCircle4= viewCircle.findViewById(R.id.im_circle4);
        ivCircle5= viewCircle.findViewById(R.id.im_circle5);
        ivCircle6= viewCircle.findViewById(R.id.im_circle6);
        ivCircle7= viewCircle.findViewById(R.id.im_circle7);
        ivCircle8= viewCircle.findViewById(R.id.im_circle8);
        ivCircle9= viewCircle.findViewById(R.id.im_circle9);
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
        btn_calculate = viewCircle.findViewById(R.id.btn_calculate);
        et_speaker_output = viewCircle.findViewById(R.id.et_speaker_output);
        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO change background color
                int power = Integer.parseInt(et_speaker_output.getText().toString());
                if(power>=200 ){
                    ivCircle9.setColorFilter(getResources().getColor(R.color.circle_9_DarkRed));
                    ivCircle8.setColorFilter(getResources().getColor(R.color.circle_8_Red));
                    ivCircle7.setColorFilter(getResources().getColor(R.color.circle_75_DarkOrange));
                    ivCircle6.setColorFilter(getResources().getColor(R.color.circle_7_Orange));
                    ivCircle5.setColorFilter(getResources().getColor(R.color.circle_6_LightOrange));
                    ivCircle4.setColorFilter(getResources().getColor(R.color.circle_55_DarkYellow));
                    ivCircle3.setColorFilter(getResources().getColor(R.color.circle_5_Yellow));
                    ivCircle2.setColorFilter(getResources().getColor(R.color.circle_45_LightYellow));
                    ivCircle1.setColorFilter(getResources().getColor(R.color.circle_4_Lime));
                    ivCircle0.setColorFilter(getResources().getColor(R.color.circle_25_Teal));
                    mFrameBackground.setBackgroundColor(getResources().getColor(R.color.circle_2_LightBlue));
                }
                if(power>=50&&power<200){
                    ivCircle9.setColorFilter(getResources().getColor(R.color.circle_8_Red));
                    ivCircle8.setColorFilter(getResources().getColor(R.color.circle_75_DarkOrange));
                    ivCircle7.setColorFilter(getResources().getColor(R.color.circle_7_Orange));
                    ivCircle6.setColorFilter(getResources().getColor(R.color.circle_6_LightOrange));
                    ivCircle5.setColorFilter(getResources().getColor(R.color.circle_55_DarkYellow));
                    ivCircle4.setColorFilter(getResources().getColor(R.color.circle_5_Yellow));
                    ivCircle3.setColorFilter(getResources().getColor(R.color.circle_45_LightYellow));
                    ivCircle2.setColorFilter(getResources().getColor(R.color.circle_4_Lime));
                    ivCircle1.setColorFilter(getResources().getColor(R.color.circle_35_LightGreen));
                    ivCircle0.setColorFilter(getResources().getColor(R.color.circle_3_Green));
                    mFrameBackground.setBackgroundColor(getResources().getColor(R.color.circle_25_Teal));
                }
                if(power>=30&&power<50){
                    ivCircle9.setColorFilter(getResources().getColor(R.color.circle_7_Orange));
                    ivCircle8.setColorFilter(getResources().getColor(R.color.circle_6_LightOrange));
                    ivCircle7.setColorFilter(getResources().getColor(R.color.circle_55_DarkYellow));
                    ivCircle6.setColorFilter(getResources().getColor(R.color.circle_5_Yellow));
                    ivCircle5.setColorFilter(getResources().getColor(R.color.circle_45_LightYellow));
                    ivCircle4.setColorFilter(getResources().getColor(R.color.circle_4_Lime));
                    ivCircle3.setColorFilter(getResources().getColor(R.color.circle_35_LightGreen));
                    ivCircle2.setColorFilter(getResources().getColor(R.color.circle_3_Green));
                    ivCircle1.setColorFilter(getResources().getColor(R.color.circle_25_Teal));
                    ivCircle0.setColorFilter(getResources().getColor(R.color.circle_2_LightBlue));
                    mFrameBackground.setBackgroundColor(getResources().getColor(R.color.circle_15_Blue));
                }
                if(power>=15&&power<30){
                    ivCircle9.setColorFilter(getResources().getColor(R.color.circle_6_LightOrange));
                    ivCircle8.setColorFilter(getResources().getColor(R.color.circle_55_DarkYellow));
                    ivCircle7.setColorFilter(getResources().getColor(R.color.circle_5_Yellow));
                    ivCircle6.setColorFilter(getResources().getColor(R.color.circle_45_LightYellow));
                    ivCircle5.setColorFilter(getResources().getColor(R.color.circle_4_Lime));
                    ivCircle4.setColorFilter(getResources().getColor(R.color.circle_35_LightGreen));
                    ivCircle3.setColorFilter(getResources().getColor(R.color.circle_3_Green));
                    ivCircle2.setColorFilter(getResources().getColor(R.color.circle_25_Teal));
                    ivCircle1.setColorFilter(getResources().getColor(R.color.circle_2_LightBlue));
                    ivCircle0.setColorFilter(getResources().getColor(R.color.circle_15_Blue));
                    mFrameBackground.setBackgroundColor(getResources().getColor(R.color.circle_1_DarkBlue));
                }
                if(power>0&&power<15){
                    ivCircle9.setColorFilter(getResources().getColor(R.color.circle_5_Yellow));
                    ivCircle8.setColorFilter(getResources().getColor(R.color.circle_45_LightYellow));
                    ivCircle7.setColorFilter(getResources().getColor(R.color.circle_4_Lime));
                    ivCircle6.setColorFilter(getResources().getColor(R.color.circle_35_LightGreen));
                    ivCircle5.setColorFilter(getResources().getColor(R.color.circle_3_Green));
                    ivCircle4.setColorFilter(getResources().getColor(R.color.circle_25_Teal));
                    ivCircle3.setColorFilter(getResources().getColor(R.color.circle_2_LightBlue));
                    ivCircle2.setColorFilter(getResources().getColor(R.color.circle_15_Blue));
                    ivCircle1.setColorFilter(getResources().getColor(R.color.circle_1_DarkBlue));
                    ivCircle0.setColorFilter(getResources().getColor(R.color.circle_1_DarkBlue));
                    mFrameBackground.setBackgroundColor(getResources().getColor(R.color.circle_1_DarkBlue));
                }

            }

        });
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
            startActivity(new Intent(PagaActivity.this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
