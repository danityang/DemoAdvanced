package com.cdemo.threelevelcache.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cdemo.threelevelcache.R;
import com.cdemo.threelevelcache.adapter.CommonAdapter;
import com.cdemo.threelevelcache.entity.ImageUrls;
import com.cdemo.threelevelcache.entity.ItemEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CommonAdapter adapter;
    List<ItemEntity> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        initData();
        adapter = new CommonAdapter(MainActivity.this, mList, R.layout.item);
        recyclerView.setAdapter(adapter);
    }


    private void initData() {
        for (int i = 0; i < ImageUrls.imageThumbUrls.length; i++) {
            ItemEntity itemEntity = new ItemEntity(ImageUrls.imageThumbUrls[i]);
            mList.add(itemEntity);
        }
    }
}
