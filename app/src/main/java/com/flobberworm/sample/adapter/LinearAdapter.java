package com.flobberworm.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.flobberworm.sample.R;
import com.flobberworm.sample.holder.LinearViewHolder;

import java.util.List;

/**
 * LinearAdapter
 * Created by Kornan on 2017/12/18.
 */

public class LinearAdapter extends RecyclerView.Adapter<LinearViewHolder> {
    private LayoutInflater layoutInflater;
    private List<String> dataList;

    public LinearAdapter(Context context, List<String> dataList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @Override
    public LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(layoutInflater.inflate(R.layout.item_linear_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(LinearViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
