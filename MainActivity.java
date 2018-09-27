package com.example.android.tsi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.tsi.utilities.ApiKey;
import com.example.android.tsi.utilities.SystemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.example.android.tsi.utilities.ApiKey.bannerAdKey;

public class MainActivity extends AppCompatActivity implements SystemAdapter.SystemOnClickInterface  {
    @BindView(R.id.rv_system_name)RecyclerView mRecyclerView;
    @BindView(R.id.adViewBanner) AdView adViewBanner;
    private SystemAdapter mSystemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mSystemAdapter = new SystemAdapter(this);
        mRecyclerView.setAdapter(mSystemAdapter);
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBanner.loadAd(adRequest);
    }

    @Override
    public void onClick(int index) {
        Log.d("MainAct onClick", "click index "+index);
        Intent intent =new
                Intent(MainActivity.this, MainActivity.class);//had to initialize intent
        switch (index){
            case 0: intent = new
                    Intent(MainActivity.this, PagaActivity.class);
                    break;
            case 1: intent = new
                    Intent(MainActivity.this, PowerCableActivity.class);
                    break;
            case 2: intent = new
                    Intent(MainActivity.this, PowerLoadsActivity.class);
                    break;
            case 3: intent = new
                    Intent(MainActivity.this, TaskListActivity.class);
                    break;
        }
        startActivity(intent);
    }
}
