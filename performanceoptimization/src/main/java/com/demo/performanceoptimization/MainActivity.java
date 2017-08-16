package com.demo.performanceoptimization;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * 性能优化
 */
public class MainActivity extends AppCompatActivity {

    ImageView img;
    BitMapUtil bitMapUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.img);

        // TODO 图片压缩处理
        bitMapUtil = new BitMapUtil(MainActivity.this);
        Bitmap bitmap = bitMapUtil.compressBitmap(R.mipmap.sh_disney01, 100, 100);
        img.setImageBitmap(bitmap);
    }


    /**
     * TODO 根据内存级别做资源释放
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            // TODO TRIM_MEMORY_UI_HIDDEN回调只有当我们程序中的所有UI组件全部不可见的时候才会触发
            case TRIM_MEMORY_UI_HIDDEN:
                // TODO 进行资源释放操作
                break;
            // TODO
            case TRIM_MEMORY_RUNNING_LOW:
                break;
        }
    }
}
