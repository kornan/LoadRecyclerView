package com.flobberworm.sample;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.flobberworm.load.ItemClickSupport;
import com.flobberworm.load.LoadStatusAdapter;
import com.flobberworm.load.OnLoadMoreListener;
import com.flobberworm.load.RecyclerManager;
import com.flobberworm.sample.adapter.LinearAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.flobberworm.load.LoadStatusAdapter.Status.STATUS_LOADING;

public class LinearLayoutActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, DataAsyncTask.OnDataEvent, OnLoadMoreListener, ItemClickSupport.OnItemClickListener {
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<String> dataList;
    private LoadStatusAdapter loadStatusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout);

        toolbar = findViewById(R.id.toolbar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);

        dataList = new ArrayList<>();
        dataList.addAll(Sample.getSample(20));
        initViews();
    }

    private void initViews() {
        requestData();
        swipeRefreshLayout.setRefreshing(true);

        LinearAdapter linearAdapter = new LinearAdapter(this, dataList);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerManager recyclerManager = new RecyclerManager(recyclerView);
        recyclerManager.setLoadStatusAdapter(linearAdapter);
        recyclerManager.setOnItemClickListener(this);
        recyclerManager.setOnLoadMoreListener(this);

        loadStatusAdapter = recyclerManager.getLoadStatusAdapter();
        loadStatusAdapter.setStatus(LoadStatusAdapter.Status.STATUS_LOADING);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        if (position < dataList.size()) {
            Toast.makeText(getBaseContext(), dataList.get(position), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoadMore() {
//        if (swipeRefreshLayout.isRefreshing()) {
//            return;
//        }
        requestData();
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    private void requestData() {
        new DataAsyncTask(this).execute();
    }

    @Override
    public void complete(List<String> data) {
        int start = dataList.size();
        dataList.addAll(data);
        loadStatusAdapter.notifyItemRangeInserted(start, data.size());

        swipeRefreshLayout.setRefreshing(false);
    }
}
