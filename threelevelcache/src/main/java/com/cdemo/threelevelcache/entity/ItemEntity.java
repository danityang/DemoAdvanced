package com.cdemo.threelevelcache.entity;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by yangdi on 2017/8/8.
 */

public class ItemEntity {

    static Context mContext;
    private String imgUrls;

    public ItemEntity(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    @BindingAdapter("imgRec")
    public static void setImg(ImageView imageView, String imgUrls) {
        DataCache dataCache = new DataCache(mContext);
        dataCache.getView(imageView, imgUrls);
    }

}
