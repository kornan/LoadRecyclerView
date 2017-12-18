package com.flobberworm.load;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * LoadMoreAdapter
 * Created by Kornan on 2017/11/23.
 */

public class LoadStatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_LOAD_MORE = -1;

    public enum Status {
        STATUS_LOADING,
        //        STATUS_PRE_LOADING,
        STATUS_LOAD_FAILURE,
        STATUS_NO_MORE,
        STATUS_LOAD_HIDE
    }

    private Status status = Status.STATUS_LOAD_HIDE;
    private RecyclerView.Adapter adapter;
    private View loadMoreView;
    private int loadMoreLayoutId;

    public LoadStatusAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        loadMoreLayoutId = R.layout.item_refresh_bottom;
    }

    public LoadStatusAdapter(RecyclerView.Adapter adapter, View loadMoreView) {
        this.adapter = adapter;
        this.loadMoreView = loadMoreView;
    }

    public LoadStatusAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, int layoutId) {
        this.adapter = adapter;
        this.loadMoreLayoutId = layoutId;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public void setLoadMoreView(View loadMoreView) {
        this.loadMoreView = loadMoreView;
    }

    public void setLoadMoreLayoutId(int loadMoreLayoutId) {
        this.loadMoreLayoutId = loadMoreLayoutId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void notifyStatusChanged() {
        this.notifyItemChanged(getItemCount() - 1);
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Is loading
     *
     * @return boolean
     */
    public boolean isLoading() {
        return status == Status.STATUS_LOADING;
    }

    /**
     * Is show loadView
     *
     * @return boolean
     */
    private boolean isShowLoadView() {
        return status != Status.STATUS_LOAD_HIDE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            View view;
            if (loadMoreView != null) {
                view = loadMoreView;
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(loadMoreLayoutId, parent, false);
            }
            return new LoadViewHolder(view);
        }
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position >= adapter.getItemCount()) {
            LoadViewHolder loadViewHolder = (LoadViewHolder) holder;
            loadViewHolder.setStatus(status);

            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams staggeredGridLayoutParams = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                staggeredGridLayoutParams.setFullSpan(true);
            }

        } else {
            adapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= adapter.getItemCount()) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return adapter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount() + (isShowLoadView() ? 1 : 0);
    }
}
