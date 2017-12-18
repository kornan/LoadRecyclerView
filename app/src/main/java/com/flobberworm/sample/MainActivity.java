package com.flobberworm.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        findViewById(R.id.linearLayoutManager).setOnClickListener(this);
        findViewById(R.id.gridLayoutManager).setOnClickListener(this);
        findViewById(R.id.staggeredGridLayoutManager).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linearLayoutManager:
                startActivity(new Intent(this, LinearLayoutActivity.class));
                break;
            case R.id.gridLayoutManager:
                startActivity(new Intent(this, GridLayoutActivity.class));
                break;
            case R.id.staggeredGridLayoutManager:
                startActivity(new Intent(this, StaggeredGridLayoutActivity.class));
                break;
        }
    }
}
