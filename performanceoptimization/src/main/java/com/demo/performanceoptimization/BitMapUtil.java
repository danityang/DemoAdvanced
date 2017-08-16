package com.demo.performanceoptimization;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by yangdi on 2017/8/15.
 * TODO BitMap图片优化
 */
public class BitMapUtil {

    private Context mContext;

    public BitMapUtil(Context context) {
        this.mContext = context;
    }

    /**
     * TODO BitmapFactory这个类提供了多个解析方法(decodeByteArray, decodeFile, decodeResource等)用于创建Bitmap对象，
     * TODO 我们应该根据图片的来源选择合适的方法。
     * TODO 比如SD卡中的图片可以使用decodeFile方法，网络上的图片可以使用decodeStream方法，
     * TODO 资源文件中的图片可以使用decodeResource方法
     */
    // TODO FactoryOptions
    public void bitMapFactoryOptions() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // TODO inJustDecodeBounds=true,解析时只对对象（图片）的宽高属性进行解析，禁止为bitmap分配内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.sh_disney01, options);
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = options.inSampleSize;
        String mimeTYpe = options.outMimeType;
    }


    /**
     * TODO 合理压缩图片
     */
    public Bitmap compressBitmap(int resId, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(mContext.getResources(), resId, options);
        options.inSampleSize = calInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(mContext.getResources(), resId, options);
    }

    /**
     * TODO 图片压缩 InSampleSize
     */
    public int calInSampleSize(BitmapFactory.Options options, int rwidth, int rheight) {
        int result = 1;
        // TODO 获取图片的实际宽高
        int width = options.outWidth;
        int height = options.outHeight;
        if (width > rwidth || height > rheight) {
            int fwidth = Math.round(width / rwidth);
            int fheight = Math.round(height / rheight);
            result = fheight < fwidth ? fheight : fwidth;
        }
        return result;
    }
}
