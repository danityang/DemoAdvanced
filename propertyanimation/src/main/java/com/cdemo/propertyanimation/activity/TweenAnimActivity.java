package com.cdemo.propertyanimation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.cdemo.propertyanimation.R;

/**
 * Created by yangdi on 2017/8/3.
 */

public class TweenAnimActivity extends BaseActivity {

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tween_anim_layout);
    }


    public void img1Click(View view) {
        Animation anim = AnimationUtils.loadAnimation(TweenAnimActivity.this, R.anim.tween_anim);
        view.startAnimation(anim);
    }

    public void alpha(View v){
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        v.startAnimation(anim);
    }

    public void rotate(View v){
        RotateAnimation anim = new RotateAnimation(0.0f, -360f, v.getWidth()/2, v.getHeight()/2);
        anim.setDuration(1000);
        v.startAnimation(anim);
    }

    public void scale(View v){
        ScaleAnimation anim = new ScaleAnimation(1.0f, 0.0f, 0.0f, 1.0f, v.getWidth() / 2, v.getHeight() / 2);
        anim.setDuration(1000);
        v.startAnimation(anim);
    }

    public void translate(View v){
        TranslateAnimation anim = new TranslateAnimation(0.0f, 10.0f, 100f, 200f);
        anim.setDuration(1000);
        v.startAnimation(anim);
    }

    public void img2Click(View view) {
        AnimationSet anim = new AnimationSet(this, null);
        AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        RotateAnimation rotate = new RotateAnimation(0.0f, -360f, view.getWidth()/2, view.getHeight()/2);
        anim.setDuration(1000);
        anim.addAnimation(alpha);
        anim.addAnimation(rotate);
        view.startAnimation(anim);
    }
}
