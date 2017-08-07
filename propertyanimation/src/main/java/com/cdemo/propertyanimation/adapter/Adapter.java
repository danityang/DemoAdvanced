package com.cdemo.propertyanimation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cdemo.propertyanimation.R;

import java.util.List;

/**
 * Created by yangdi on 2017/7/14.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> list;

    public Adapter() {
    }

    public Adapter(List list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        holder.itemText.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemText;

        public ViewHolder(View view) {
            super(view);
            itemText = (TextView) view.findViewById(R.id.text);
        }
    }

}
