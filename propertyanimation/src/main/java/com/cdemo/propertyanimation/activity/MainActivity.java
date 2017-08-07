package com.cdemo.propertyanimation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cdemo.propertyanimation.R;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.text)
    TextView text;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tweenAnimClick(View view){
        startActivity(new Intent(MainActivity.this, TweenAnimActivity.class));
    }

    public void FrameAnimClick(View view){
        startActivity(new Intent(MainActivity.this, FrameAnimActivity.class));
    }

    public void basicObjectAnimClick(View view){
        startActivity(new Intent(MainActivity.this, BasicObjectAnimActivity.class));
    }
    public void xmlAnimClick(View view){
        startActivity(new Intent(MainActivity.this, XmlFileObjectAnimActivity.class));
    }

    public void evaluatorCliick(View view){
        startActivity(new Intent(MainActivity.this, EvaluatorObjectAnimActivity.class));
    }
    public void colorEvaluatorClick(View view){
        startActivity(new Intent(MainActivity.this, ColorEvaluatorObjectAnimActivity.class));
    }

    public void viewPropertyAnimatorClick(View view){
        startActivity(new Intent(MainActivity.this, ViewPropertyAnimatorActivity.class));
    }
    public void layoutTransitionClick(View view){
        startActivity(new Intent(MainActivity.this, LayoutTransitionAnimActivity.class));
    }

}
