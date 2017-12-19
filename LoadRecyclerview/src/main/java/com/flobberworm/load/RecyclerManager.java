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

public final class RecyclerManager {

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
        initFooter();
        initLoadMore();
    }

    public void init() {
        this.mInflater = LayoutInflater.from(recyclerView.getContext());
        initFooter();
        initLoadMore();
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setLoadStatusAdapter(RecyclerView.Adapter adapter) {
        this.loadStatusAdapter = new LoadStatusAdapter(adapter);
        this.recyclerView.setAdapter(loadStatusAdapter);

    }

    /**
     * @param layoutManager
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
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

    public void setLoadStatusAdapter(LoadStatusAdapter loadStatusAdapter) {
        this.loadStatusAdapter = loadStatusAdapter;
        this.recyclerView.setAdapter(loadStatusAdapter);
    }

    public LoadStatusAdapter getLoadStatusAdapter() {
        return loadStatusAdapter;
    }

    /**
     * setOnItemClickListener
     *
     * @param onItemClickListener ItemClickSupport.OnItemClickListener
     */
    public void setOnItemClickListener(ItemClickSupport.OnItemClickListener onItemClickListener) {
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(onItemClickListener);
    }

    /**
     * config footer
     */
    private void initFooter() {
        //default footerView
        footerView = mInflater.inflate(R.layout.item_refresh_bottom, null);
    }

    /**
     * config load more
     */
    private void initLoadMore() {
        layoutManager = recyclerView.getLayoutManager();
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

    /**
     * set bottom loading listener
     *
     * @param onLoadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /**
     * add footer view
     *
     * @param view
     */
    public void addFooterView(View view) {
        this.footerView = view;
    }
}
