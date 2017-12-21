package com.flobberworm.load;

import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Load ViewHolder
 * Created by KORNAN on 2017/11/23.
 */
public abstract class LoadViewHolder extends RecyclerView.ViewHolder {
    private LoadStatusAdapter.Status status;

    public LoadViewHolder(View itemView) {
        super(itemView);
        findView(itemView);
//        setStatus(STATUS_LOADING);

    }

    public abstract void findView(View itemView);

    public LoadStatusAdapter.Status getStatus() {
        return status;
    }

    public void setStatus(LoadStatusAdapter.Status status) {
        this.status = status;
    }
}
