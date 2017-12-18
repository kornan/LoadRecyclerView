package com.flobberworm.sample.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.flobberworm.sample.R;

/**
 * LinearViewHolder
 * Created by Kornan on 2017/12/18.
 */
public class LinearViewHolder extends RecyclerView.ViewHolder {
    private TextView tvContent;

    public LinearViewHolder(View itemView) {
        super(itemView);
        tvContent = itemView.findViewById(R.id.tvContent);
    }

    public void bind(String str) {
        tvContent.setText(str);
    }
}
