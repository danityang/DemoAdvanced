package com.demo.commonadapter;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangdi on 2017/10/16.
 */

public class LoadMoreSmple extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private CommonAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<String> mDataList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_more_sample);
        // recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        // swipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(LoadMoreSmple.this, 1));

        mRecyclerView.addOnScrollListener(rvOnScrollListener);

        setData();
        // 初始化适配器数据
        initAdapter();
    }

    private void setData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDataList.add("this " + i + " item is sample");
        }
    }


    private void initAdapter() {

        mAdapter = new CommonAdapter<String, BaseViewHolder>(mDataList) {
            @Override
            protected int getLayoutId() {
                return R.layout.item;
            }

            @Override
            protected void convert(BaseViewHolder holder, String item) {
                holder.setText(R.id.tv, item);
            }

        };

        mRecyclerView.setAdapter(mAdapter);
    }

    RecyclerView.OnScrollListener rvOnScrollListener = new RecyclerView.OnScrollListener() {
        int totalItemCount;
        int lastVisibleItemPosition;
        private LinearLayoutManager linearLayoutManager;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            }
            totalItemCount = mRecyclerView.getAdapter().getItemCount();
            lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            if (!mSwipeRefreshLayout.isRefreshing() && newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItemPosition + 1 == totalItemCount) {
                for (int i = 0; i < 10; i++) {
                    mDataList.add("this " + i + " add item");
                }
                mAdapter.notifyDataSetChanged();
            } else {

            }
        }
    };

    @Override
    public void onRefresh() {

    }
}
