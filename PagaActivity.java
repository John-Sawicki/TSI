package com.example.android.tsi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

public class PagaActivity extends AppCompatActivity {
    @BindView(R.id.im_background)FrameLayout mFrameBackground;
    @BindView(R.id.im_circle1) ImageView ivCircle1;
    @BindView(R.id.im_circle2)ImageView ivCircle2;
    @BindView(R.id.im_circle3)ImageView ivCircle3;
    @BindView(R.id.im_circle4)ImageView ivCircle4;
    @BindView(R.id.im_circle5)ImageView ivCircle5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paga);
        View viewCircle = findViewById(R.id.lo_top_right);
        View viewDb = findViewById(R.id.lo_bottom_left);



    }
}
