package com.cdemo.propertyanimation.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cdemo.propertyanimation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangdi on 2017/8/2.
 */

public class BasicObjectAnimActivity extends BaseActivity {


    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.textview)
    TextView textView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_object_anim_layout);
        ButterKnife.bind(this);

        // 通过ValueAnimator值的变化
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f,1f);
        valueAnimator.setTarget(text);
        valueAnimator.setDuration(2000);
        valueAnimator.start();

        // 动画监听
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        // 接口太多，可以选择AnimatorListenerAdapter，来选择需要的接口方法，其为abstract类
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });

        // ObjectAnimator作用于TextView
        ObjectAnimator anim = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f);
        anim.setDuration(1000);
        anim.start();
    }

    public void imgClick(View view) {
        ObjectAnimator imgAnim = ObjectAnimator.ofFloat(view, "rotationX", 0.0f, 360.0f);
        imgAnim.setDuration(2000);
        imgAnim.start();
    }


    // UpdateListener实现多个动画
    public void updateListenerAnimClick(final View view) {
        ObjectAnimator imgAnim = ObjectAnimator.ofFloat(view, "scale", 1f, 2f, 1f);
        imgAnim.setDuration(2000);
        imgAnim.start();

        imgAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setScaleX((Float) animation.getAnimatedValue());
                view.setScaleY((Float) animation.getAnimatedValue());
            }
        });
    }

    // PropertyValuesHolder实现多个组合动画
    public void propertyValuesHolderAnimClick(final View view) {
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX",1f,2f,1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY",1f,2f,1f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha",1f,0f,1f);
        ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY,alpha).setDuration(500).start();
    }

    // AnimatorSet实现多个组合动画
    public void animSetClick(View view){
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1f,0f,1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f,3f,1f);
        ObjectAnimator scalY = ObjectAnimator.ofFloat(view, "scaleX", 1f,3f,1f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0f,360f);
        AnimatorSet animset = new AnimatorSet();
        animset.playTogether(alpha,scaleX,scalY,rotation);
//        animset.play(alpha).with(scaleX).with(scalY).after(rotation);
        animset.setDuration(1000);
        animset.start();
    }

}
