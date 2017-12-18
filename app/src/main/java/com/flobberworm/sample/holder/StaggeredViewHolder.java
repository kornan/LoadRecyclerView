package com.flobberworm.sample.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flobberworm.sample.R;

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

    public void bind(String str) {
        tvContent.setText(str);
        Random random = new Random(System.currentTimeMillis());
        if (random.nextInt() % 2 == 0) {
            ivIcon.setImageResource(R.mipmap.a);
        }
//        else if (random.nextInt() % 2 == 1) {
//            ivIcon.setImageResource(R.mipmap.b);
//        }
        else {
            ivIcon.setImageResource(R.mipmap.c);
        }

    }
}
