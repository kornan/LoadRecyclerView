package com.flobberworm.load;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import static com.flobberworm.load.LoadStatusAdapter.ITEM_TYPE_LOAD_MORE;

/**
 * RecyclerView Manager
 * Created by Kornan on 2017/11/14.
 */

public final class RecyclerManager implements IRecyclerManager, ILoadStatus {

    private RecyclerView recyclerView;
    private LayoutInflater mInflater;
    private View footerView;
    private OnLoadMoreListener onLoadMoreListener;
    private RecyclerView.LayoutManager layoutManager;
    private LoadStatusAdapter loadStatusAdapter;

    private boolean isInTheBottom = false;
    /**
     * reachBottomRow = 1;(default)
     * mean : when the lastVisibleRow is lastRow , call the onReachBottom();
     * reachBottomRow = 2;
     * mean : when the lastVisibleRow is Penultimate Row , call the onReachBottom();
     * And so on
     */
    private int reachBottomRow = 1;

    public RecyclerManager() {

    }

    public RecyclerManager(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.mInflater = LayoutInflater.from(recyclerView.getContext());
        setDefaultFooterView();
        setLoadMore();
    }

    /**
     * config footer
     */
    private void setDefaultFooterView() {
        //default footerView
        footerView = mInflater.inflate(R.layout.item_load_default_fw, null);
    }

    /**
     * config load more
     */
    private void setLoadMore() {
//        layoutManager = recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (onLoadMoreListener != null) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager == null) { //it maybe unnecessary
                        throw new RuntimeException("LayoutManager is null,Please check it!");
                    }
                    RecyclerView.Adapter adapter = recyclerView.getAdapter();
                    if (adapter == null) { //it maybe unnecessary
                        throw new RuntimeException("Adapter is null,Please check it!");
                    }
                    boolean isReachBottom = false;
                    //is GridLayoutManager
                    if (layoutManager instanceof GridLayoutManager) {
                        GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                        int rowCount = adapter.getItemCount() / gridLayoutManager.getSpanCount();
                        int lastVisibleRowPosition = gridLayoutManager.findLastVisibleItemPosition() / gridLayoutManager.getSpanCount();
                        isReachBottom = (lastVisibleRowPosition >= rowCount - reachBottomRow);
                    }
                    //is LinearLayoutManager
                    else if (layoutManager instanceof LinearLayoutManager) {
                        int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        int rowCount = adapter.getItemCount();
                        if (reachBottomRow > rowCount)
                            reachBottomRow = 1;
                        isReachBottom = (lastVisibleItemPosition >= rowCount - reachBottomRow);
                    }
                    //is StaggeredGridLayoutManager
                    else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                        int spanCount = staggeredGridLayoutManager.getSpanCount();
                        int[] into = new int[spanCount];
                        int[] eachSpanListVisibleItemPosition = staggeredGridLayoutManager.findLastVisibleItemPositions(into);
                        for (int i = 0; i < spanCount; i++) {
                            if (eachSpanListVisibleItemPosition[i] > adapter.getItemCount() - reachBottomRow * spanCount) {
                                isReachBottom = true;
                                break;
                            }
                        }
                    }

                    if (!isReachBottom) {
                        isInTheBottom = false;
                    } else if (!isInTheBottom) {
                        onLoadMoreListener.onLoadMore();
                        isInTheBottom = true;
                    }
                }
            }
        });

    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public LoadStatusAdapter getLoadStatusAdapter() {
        return loadStatusAdapter;
    }

    /**
     * @param layoutManager RecyclerView.LayoutManager
     */
    @Override
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return loadStatusAdapter.getItemViewType(position) == ITEM_TYPE_LOAD_MORE ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
//        if (adapter instanceof LoadStatusAdapter) {
//            this.loadStatusAdapter = (LoadStatusAdapter) adapter;
//        } else {
        this.loadStatusAdapter = new LoadStatusAdapter(adapter);
//        }
        this.recyclerView.setAdapter(loadStatusAdapter);
    }

    public void setAdapter(LoadStatusAdapter adapter) {
        this.loadStatusAdapter = adapter;
        this.recyclerView.setAdapter(loadStatusAdapter);
    }

    /**
     * setOnItemClickListener
     *
     * @param onItemClickListener ItemClickSupport.OnItemClickListener
     */
    @Override
    public void setOnItemClickListener(ItemClickSupport.OnItemClickListener onItemClickListener) {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void setOnItemLongClickListener(ItemClickSupport.OnItemLongClickListener onItemLongClickListener) {
        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(onItemLongClickListener);
    }

    /**
     * set bottom loading listener
     *
     * @param onLoadMoreListener
     */
    @Override
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /**
     * set footer view
     *
     * @param view
     */
    @Override
    public void setFooterView(View view) {
        this.footerView = view;
    }

    @Override
    public void setStatus(LoadStatusAdapter.Status status) {
        loadStatusAdapter.setStatus(status);
    }

    @Override
    public boolean isLoading() {
        return loadStatusAdapter.getStatus() == LoadStatusAdapter.Status.STATUS_LOADING;
    }

    @Override
    public boolean isHideStatus() {
        return loadStatusAdapter.getStatus() == LoadStatusAdapter.Status.STATUS_LOAD_HIDE;
    }

    @Override
    public boolean isNoMoreStatus() {
        return loadStatusAdapter.getStatus() == LoadStatusAdapter.Status.STATUS_NO_MORE;
    }

    @Override
    public boolean isFailureStatus() {
        return loadStatusAdapter.getStatus() == LoadStatusAdapter.Status.STATUS_LOAD_FAILURE;
    }

    @Override
    public void notifyStatusChanged() {
        loadStatusAdapter.notifyStatusChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        loadStatusAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyItemChanged(int position) {
        loadStatusAdapter.notifyItemChanged(position);
    }

    @Override
    public void notifyItemChanged(int position, Object payload) {
        loadStatusAdapter.notifyItemChanged(position, payload);
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        loadStatusAdapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void notifyItemRangeChanged(int positionStart, int itemCount, Object payload) {
        loadStatusAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
    }

    @Override
    public void notifyItemInserted(int position) {
        loadStatusAdapter.notifyItemInserted(position);
    }

    @Override
    public void notifyItemMoved(int fromPosition, int toPosition) {
        loadStatusAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        loadStatusAdapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void notifyItemRemoved(int position) {
        loadStatusAdapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyItemRangeRemoved(int positionStart, int itemCount) {
        loadStatusAdapter.notifyItemRangeRemoved(positionStart, itemCount);
    }
}