package com.example.weatherapp.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.ViewPagerDetailAdapter;

public class DetailWeatherActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerDetailAdapter viewPagerDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);

        viewPager = findViewById(R.id.pager);
        viewPagerDetailAdapter = new ViewPagerDetailAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerDetailAdapter);

        tabLayout = findViewById(R.id.tablayout_detail_tabs);
        tabLayout.setupWithViewPager(viewPager);

        setUpActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // enable back button
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpActionBar() {
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("ACTION_BAR_TITLE"));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
