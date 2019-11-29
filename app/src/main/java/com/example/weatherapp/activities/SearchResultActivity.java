package com.example.weatherapp.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.ViewPagerSearchResultAdapter;

public class SearchResultActivity extends AppCompatActivity {
    ViewPager mViewPagerSearchResult;
    ViewPagerSearchResultAdapter mViewPagerSearchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mViewPagerSearchResult = findViewById(R.id.view_pager_search_result);

        setUpActionBar();
    }

    private void setUpActionBar() {
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("SEARCH_KEYWORD"));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
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
}
