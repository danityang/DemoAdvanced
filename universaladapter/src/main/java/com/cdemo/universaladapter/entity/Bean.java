package com.cdemo.universaladapter.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.cdemo.universaladapter.BR;

/**
 * Created by yangdi on 2017/8/7.
 */

public class Bean extends BaseObservable {

    private String item;

    @Bindable
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
        notifyPropertyChanged(BR.item);
    }
}