package com.flobberworm.load;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * IRecyclerManager
 * Created by Kornan on 2017/12/21.
 */

public interface IRecyclerManager {

    void setLayoutManager(RecyclerView.LayoutManager layoutManager);

    void setAdapter(RecyclerView.Adapter adapter);

//    void setAdapter(LoadStatusAdapter adapter);
//    void setLoadStatusAdapter();

    void setFooterView(View view);

    void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener);

    void setOnItemClickListener(ItemClickSupport.OnItemClickListener onItemClickListener);

    void setOnItemLongClickListener(ItemClickSupport.OnItemLongClickListener onItemLongClickListener);

}
