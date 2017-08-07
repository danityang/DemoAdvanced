package com.cdemo.propertyanimation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cdemo.propertyanimation.R;

/**
 * Created by yangdi on 2017/8/4.
 */

public class ViewPropertyAnimatorActivity extends BaseActivity {

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewproperty_anim_layout);
    }

    public void alphaClick(View view) {
        view.animate().alpha(0f);
    }

    public void scaleClick(View view) {
        view.animate().scaleX(3).scaleY(3).setDuration(500);
    }

    public void translateClick(View view) {
        view.animate().x(500).y(500).setDuration(2000);
    }

    public void rotateClick(View view) {
        view.animate().rotation(360);
    }
}
