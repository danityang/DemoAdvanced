package com.cdemo.threelevelcache.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cdemo.threelevelcache.R;
import com.cdemo.threelevelcache.entity.DataCache;
import com.cdemo.threelevelcache.entity.ItemEntity;

import java.util.List;

/**
 * Created by yangdi on 2017/8/8.
 */

public class CommonAdapter<T> extends RecyclerView.Adapter<CommonAdapter.RvHolder> {

    List<T> mDataList;
    private int mLayoutId;
    private int variableId;
    DataCache dataCache;

    public CommonAdapter(Context context, List<T> mDataList, int layoutId) {
        this.mDataList = mDataList;
        this.mLayoutId = layoutId;
        dataCache = new DataCache(context);
    }

    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new RvHolder(view);
    }

    @Override
    public void onBindViewHolder(CommonAdapter.RvHolder holder, int position) {
        dataCache.getView(holder.img, ((ItemEntity) mDataList.get(position)).getImgUrls());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    //    public abstract void onBind(RvHolder holder, int position);
    class RvHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public RvHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}


