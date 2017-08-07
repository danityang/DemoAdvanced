package com.cdemo.propertyanimation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.cdemo.propertyanimation.R;
import com.cdemo.propertyanimation.adapter.Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangdi on 2017/8/4.
 */

public class LayoutTransitionAnimActivity extends BaseActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    Adapter adapter;
    List<String> mList;
    Button addButton, delButton;
//    LayoutTransition layoutTransition;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_transition_anim_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        addButton = (Button) findViewById(R.id.add_btn);
        delButton = (Button) findViewById(R.id.del_btn);
        addButton.setOnClickListener(this);
        delButton.setOnClickListener(this);
        /*
          *LayoutTransition.APPEARING 当一个View在ViewGroup中出现时，对此View设置的动画
            LayoutTransition.CHANGE_APPEARING 当一个View在ViewGroup中出现时，对此View对其他View位置造成影响，对其他View设置的动画
            LayoutTransition.DISAPPEARING  当一个View在ViewGroup中消失时，对此View设置的动画
            LayoutTransition.CHANGE_DISAPPEARING 当一个View在ViewGroup中消失时，对此View对其他View位置造成影响，对其他View设置的动画
            LayoutTransition.CHANGE 不是由于View出现或消失造成对其他View位置造成影响，然后对其他View设置的动画。
        * */
//        layoutTransition = new LayoutTransition();
//        layoutTransition.setAnimator(LayoutTransition.APPEARING, layoutTransition.getAnimator(LayoutTransition.APPEARING));

        adapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(LayoutTransitionAnimActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(LayoutTransitionAnimActivity.this, 1));
        recyclerView.setAdapter(adapter);
        // LayoutTransition适用于在ViewGroup中添加或删除View时的动画，recycler的add和remove在集合中实现删除和增加，所以不适用？
//        recyclerView.setLayoutTransition(layoutTransition);
        setData();
    }

    private void setData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add("ITEM" + i);
        }
        adapter.setList(mList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                mList.add("NEW ITEM");
                adapter.notifyItemInserted(mList.size());
                break;
            case R.id.del_btn:
                if (mList.size() > 0) {
                    mList.remove(mList.size() - 1);
                    adapter.notifyItemRemoved(mList.size());
                }
                break;
        }
//        adapter.notifyDataSetChanged();
    }
}
