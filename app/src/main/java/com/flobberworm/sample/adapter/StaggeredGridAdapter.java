package com.flobberworm.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.flobberworm.sample.R;
import com.flobberworm.sample.holder.LinearViewHolder;
import com.flobberworm.sample.holder.StaggeredViewHolder;

import java.util.List;

/**
 * StaggeredGridAdapter
 * Created by Kornan on 2017/12/18.
 */

public class StaggeredGridAdapter extends RecyclerView.Adapter<StaggeredViewHolder> {
    private LayoutInflater layoutInflater;
    private List<String> dataList;

    public StaggeredGridAdapter(Context context, List<String> dataList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @Override
    public StaggeredViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StaggeredViewHolder(layoutInflater.inflate(R.layout.item_staggered_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(StaggeredViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
