package com.cdemo.propertyanimation.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cdemo.propertyanimation.R;

/**
 * Created by yangdi on 2017/8/3.
 */

public class XmlFileObjectAnimActivity extends BaseActivity {

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_file_object_anim_layout);
    }

    public void img1Click(View view) {
        Animator animator = AnimatorInflater.loadAnimator(XmlFileObjectAnimActivity.this, R.animator.alpha_anim);
        animator.setTarget(view);
        animator.start();
    }

    public void img2Click(View view) {
        Animator animator = AnimatorInflater.loadAnimator(XmlFileObjectAnimActivity.this, R.animator.scale_anim);
        animator.setTarget(view);
        animator.start();
    }

    public void img3Click(View view) {
        Animator animator = AnimatorInflater.loadAnimator(XmlFileObjectAnimActivity.this, R.animator.group_anim);
        animator.setTarget(view);
        animator.start();
    }
}
