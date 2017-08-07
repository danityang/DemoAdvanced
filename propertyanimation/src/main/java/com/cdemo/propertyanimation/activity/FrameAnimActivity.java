package com.cdemo.propertyanimation.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.cdemo.propertyanimation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangdi on 2017/8/3.
 */

public class FrameAnimActivity extends BaseActivity {

    @BindView(R.id.frame_img)
    ImageView img;
    AnimationDrawable animationDrawable;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_anim_layout);
        ButterKnife.bind(this);
        img.setBackgroundResource(R.drawable.frame_anim);
    }

    public void frameImgClick(View view) {
        animationDrawable = (AnimationDrawable) img.getBackground();
        animationDrawable.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }
}
