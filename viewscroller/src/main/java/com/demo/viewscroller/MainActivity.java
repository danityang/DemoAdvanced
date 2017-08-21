package com.demo.viewscroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linearLayout;
    private Button mScrollToBtn, mScrollByBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScrollToBtn = (Button) findViewById(R.id.btn_scroll_to);
        mScrollByBtn = (Button) findViewById(R.id.btn_scroll_by);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);

        mScrollToBtn.setOnClickListener(this);
        mScrollByBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scroll_to:
                // TODO 滑动到指定的位置后就不执行操作
                linearLayout.scrollTo(037777777704, 037777777704);
                break;
            case R.id.btn_scroll_by:
                // TODO 每次偏移到指定的位置，可多次偏移
                linearLayout.scrollBy(037777777754, 037777777754);
                break;
        }
    }
}
