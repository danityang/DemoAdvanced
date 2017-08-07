package com.cdemo.propertyanimation.cview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.cdemo.propertyanimation.adapter.ColorEvaluator;
import com.cdemo.propertyanimation.adapter.PointEvaluator;
import com.cdemo.propertyanimation.entity.Point;

/**
 * Created by yangdi on 2017/8/3.
 */

public class ColorView extends View {

    public static final float RADIUS = 50f;
    private Paint mPaint;
    private Point mPoint;

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public ColorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPoint == null) {
            mPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        }else{
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = mPoint.getX();
        float y = mPoint.getY();
        // 画圆
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    private void startAnimation() {
        Point startPoint = new Point(RADIUS, RADIUS);
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        // PointEvaluator里的值是动态改变的，赋予数轴xy值不断改变
        ValueAnimator valueAnim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPoint = (Point) animation.getAnimatedValue();
                // 通过动画的变化（增长值）不断重绘，实现Android屏幕x,y坐标值不断增加（Android手机屏幕可看成数学中的数轴）
                invalidate();
            }
        });
        // 添加插值器效果
        valueAnim.setInterpolator(new BounceInterpolator());
        ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(),
                "#0000FF", "#FF0000");
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(valueAnim).with(anim2);
        valueAnim.setDuration(6000);
        valueAnim.start();
    }
}
