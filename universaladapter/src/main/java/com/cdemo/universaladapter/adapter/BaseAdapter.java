package com.cdemo.universaladapter.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yangdi on 2017/8/7.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.RvHolder> {


    List<T> mDataList;
    private int mLayoutId;
    private int variableId;


    public BaseAdapter(List<T> list, int layoutId, int variableId) {
        this.mLayoutId = layoutId;
        this.mDataList = list;
        this.variableId = variableId;
    }

    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);
        return new RvHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseAdapter.RvHolder holder, int position) {
        holder.bindData(mDataList.get(position));
        onBind(holder, position);
    }

    public abstract void onBind(RvHolder holder, int position);

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    class RvHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        public RvHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(T t) {
            binding.setVariable(variableId, t);
            binding.executePendingBindings();
        }
    }

}
