package com.cdemo.propertyanimation.adapter;

import android.animation.TypeEvaluator;

import com.cdemo.propertyanimation.entity.Point;

/**
 * Created by yangdi on 2017/8/3.
 */

public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point pStart = (Point) startValue;
        Point pEnd = (Point) endValue;
        // 通过fraction这个不断变化的值，赋予x,y值不断变化
        float x = pStart.getX() + fraction * (pEnd.getX() - pStart.getX());
        float y = pStart.getY() + fraction * (pEnd.getY() - pStart.getY());
        // 返回Point对象
        return new Point(x, y);
    }
}
