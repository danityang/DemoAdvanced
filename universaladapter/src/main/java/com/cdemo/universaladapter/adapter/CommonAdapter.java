package com.cdemo.universaladapter.adapter;

import android.view.View;

import java.util.List;

/**
 * Created by yangdi on 2017/8/7.
 */

public class CommonAdapter extends BaseAdapter {

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void itemSelect(int position);
    }

    public CommonAdapter(List list, int layoutId, int variableId) {
        super(list, layoutId, variableId);
    }

    @Override
    public void onBind(RvHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.itemSelect(position);
            }
        });
    }

}
