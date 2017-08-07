package com.cdemo.propertyanimation.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cdemo.propertyanimation.R;
import com.cdemo.propertyanimation.adapter.ColorEvaluator;
import com.cdemo.propertyanimation.cview.ColorView;

/**
 * Created by yangdi on 2017/8/3.
 */

public class ColorEvaluatorObjectAnimActivity extends BaseActivity {

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_anim_color_evaluator_activity);
        ColorView colorView = (ColorView) findViewById(R.id.color_view);
        colorViewClick(colorView);
    }

    public void colorViewClick(View view){
        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(view, "color", new ColorEvaluator(), "#0000FF","#FF0000");
        objectAnimator.setDuration(5000);
        objectAnimator.start();
    }
}
