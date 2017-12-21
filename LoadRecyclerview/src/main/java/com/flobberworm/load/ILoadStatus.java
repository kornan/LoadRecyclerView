package com.flobberworm.load;

/**
 * ILoadStatus
 * Created by Kornan on 2017/12/21.
 */

public interface ILoadStatus {
    void setStatus(LoadStatusAdapter.Status status);

    boolean isLoading();

    boolean isHideStatus();

    boolean isNoMoreStatus();

    boolean isFailureStatus();

    void notifyStatusChanged();

    void notifyDataSetChanged();

    void notifyItemChanged(int position);

    void notifyItemChanged(int position, Object payload);

    void notifyItemRangeChanged(int positionStart, int itemCount);

    void notifyItemRangeChanged(int positionStart, int itemCount, Object payload);

    void notifyItemInserted(int position);

    void notifyItemMoved(int fromPosition, int toPosition);

    void notifyItemRangeInserted(int positionStart, int itemCount);

    void notifyItemRemoved(int position);

    void notifyItemRangeRemoved(int positionStart, int itemCount);
}
