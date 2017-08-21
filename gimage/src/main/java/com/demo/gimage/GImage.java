package com.demo.gimage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * Created by yangdi on 2017/8/17.
 */

public class GImage extends ImageView {

    // 播放gif的核心类
    private Movie mMovie;
    private static String TAG = "GImage";


    public GImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GifImageView);
        int resourceId = getResourceId(typedArray);
        if (resourceId != 0){
            InputStream is = getResources().openRawResource(resourceId);
            // 使用Movie类对流进行解码
            mMovie = Movie.decodeStream(is);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mMovie == null) {
            super.onDraw(canvas);
        } else {
            playGif(canvas);
            invalidate();
        }
    }


    long mMovieStart = 0;

    private void playGif(Canvas canvas) {
        long nowTime = SystemClock.uptimeMillis();
        if (mMovieStart == 0) {
            mMovieStart = nowTime;
        }

        int duration = mMovie.duration();
        if (duration == 0) {
            duration = 1000;
        }

        int realTime = (int) ((nowTime - mMovieStart) % duration);
        mMovie.setTime(realTime);
        mMovie.draw(canvas, 0, 0);

        if ((nowTime - mMovieStart) >= duration) {
            mMovieStart = 0;
        }
    }


    private int getResourceId(TypedArray a) {
        try {
            Field field = TypedArray.class.getDeclaredField("mValue");
            field.setAccessible(true);
            TypedValue typedValueObject = (TypedValue) field.get(a);
            return typedValueObject.resourceId;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
        return 0;
    }


}
