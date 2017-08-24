package com.demo.immersivemodel;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by yangdi on 2017/8/22.
 */

public class TransparentStatusBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transparentstatusbar_layout);
        // todo -getDecorView——>隐藏状态栏
        // todo 透明状态栏栏只在5.0以上系统才有效果
        if (Build.VERSION.SDK_INT >= 21) {
            View v = getWindow().getDecorView();
            v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        // todo 隐藏ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
