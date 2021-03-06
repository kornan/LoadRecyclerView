package com.flobberworm.sample;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.flobberworm.load.ItemClickSupport;
import com.flobberworm.load.LoadStatusAdapter;
import com.flobberworm.load.OnLoadMoreListener;
import com.flobberworm.load.RecyclerManager;
import com.flobberworm.sample.adapter.LinearAdapter;
import com.flobberworm.sample.adapter.StaggeredGridAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.flobberworm.load.LoadStatusAdapter.ITEM_TYPE_LOAD_MORE;

public class StaggeredGridLayoutActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, DataAsyncTask.OnDataEvent, OnLoadMoreListener, ItemClickSupport.OnItemClickListener {
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<SampleBean> dataList;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private StaggeredGridAdapter adapter;
    private RecyclerManager recyclerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered_grid_layout);
        toolbar = findViewById(R.id.toolbar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);

        dataList = new ArrayList<>();
        List<String> data = Sample.getSample(10);
        SampleBean sampleBean;
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < data.size(); i++) {
            sampleBean = new SampleBean();
            if (random.nextInt() % 2 == 0) {
                sampleBean.rid = R.mipmap.a;
            } else {
                sampleBean.rid = R.mipmap.c;
            }
            sampleBean.title = data.get(i);
            dataList.add(sampleBean);
        }
        initViews();
        requestData();
    }

    private void initViews() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        adapter = new StaggeredGridAdapter(this, dataList);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerManager = new RecyclerManager(recyclerView);
        recyclerManager.setLayoutManager(staggeredGridLayoutManager);
        recyclerManager.setAdapter(adapter);
        recyclerManager.setOnItemClickListener(this);
        recyclerManager.setOnLoadMoreListener(this);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        if (position < dataList.size()) {
            Toast.makeText(getBaseContext(), dataList.get(position).title, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoadMore() {
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
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            dataList = new ArrayList<>();
            adapter.setDataList(dataList);
            recyclerManager.notifyDataSetChanged();
        }
        int start = dataList.size();
        SampleBean sampleBean;
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < data.size(); i++) {
            sampleBean = new SampleBean();
            if (random.nextInt() % 2 == 0) {
                sampleBean.rid = R.mipmap.a;
            } else {
                sampleBean.rid = R.mipmap.c;
            }
            sampleBean.title = data.get(i);
            dataList.add(sampleBean);
        }
        recyclerManager.setStatus(LoadStatusAdapter.Status.STATUS_LOADING);
        recyclerManager.notifyItemRangeInserted(start, data.size());
    }
}
