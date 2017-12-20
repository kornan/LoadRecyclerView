package com.flobberworm.load;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import static com.flobberworm.load.LoadStatusAdapter.Status.STATUS_LOADING;
import static com.flobberworm.load.LoadStatusAdapter.Status.STATUS_LOAD_FAILURE;
import static com.flobberworm.load.LoadStatusAdapter.Status.STATUS_NO_MORE;


/**
 * Load ViewHolder
 * Created by KORNAN on 2017/11/23.
 */
public class LoadViewHolder extends RecyclerView.ViewHolder {
    private LoadStatusAdapter.Status status;
    private ContentLoadingProgressBar progressBar;
    private TextView tvLoading;

    public LoadViewHolder(View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progress);
        tvLoading = itemView.findViewById(R.id.tv_loading);
        setStatus(STATUS_LOADING);
    }

    public LoadStatusAdapter.Status getStatus() {
        return status;
    }

    public void setStatus(LoadStatusAdapter.Status status) {
        this.status = status;
        if (status == STATUS_LOADING) {
//            progressBar.show();
            progressBar.setVisibility(View.VISIBLE);
//            tvLoading.setText(R.string.recycle_loading);
            tvLoading.setVisibility(View.GONE);
        } else if (status == STATUS_NO_MORE) {
//            progressBar.hide();
            progressBar.setVisibility(View.GONE);

            tvLoading.setText(R.string.recycle_no_more);
            tvLoading.setVisibility(View.VISIBLE);
        } else if (status == STATUS_LOAD_FAILURE) {
//            progressBar.hide();
            progressBar.setVisibility(View.GONE);

            tvLoading.setVisibility(View.VISIBLE);
            tvLoading.setText(R.string.recycle_load_failure);
        }
    }
}
