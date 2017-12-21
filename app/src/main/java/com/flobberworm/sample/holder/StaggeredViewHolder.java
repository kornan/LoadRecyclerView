package com.flobberworm.sample.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flobberworm.sample.R;
import com.flobberworm.sample.SampleBean;

import java.util.Random;

/**
 * LinearViewHolder
 * Created by Kornan on 2017/12/18.
 */
public class StaggeredViewHolder extends RecyclerView.ViewHolder {
    private TextView tvContent;
    private ImageView ivIcon;

    public StaggeredViewHolder(View itemView) {
        super(itemView);
        tvContent = itemView.findViewById(R.id.tvContent);
        ivIcon = itemView.findViewById(R.id.ivIcon);
    }

    public void bind(SampleBean bean) {
        tvContent.setText(bean.title);
        ivIcon.setImageResource(bean.rid);

    }
}
