package com.demo.immersivemodel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by yangdi on 2017/8/22.
 */

public class FullScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_layout);
        // todo -getDecorView——>隐藏状态栏
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        // todo 隐藏ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
