package com.demo.gimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

//    GImage mGifImage;
    byte[] is;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mGifImage = (GImage) findViewById(R.id.gif_imgview);
//        img = (ImageView) findViewById(R.id.img);

//        mGifImage.setImageResource(R.mipmap.ic_launcher_round);
        /*new Thread() {
            @Override
            public void run() {
                // http://img1.cyzone.cn/uploadfile/2016/0113/20160113033625936.gif
                // http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg
                is = ImgUtil.getStream("http://img1.cyzone.cn/uploadfile/2016/0113/20160113033625936.gif");
                if(is!=null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(is,0,is.length);
                    img.setImageBitmap(bitmap);
                }
            }
        }.start();*/

        /**
         * 驾驶技能 上路总结：
         * 1：转弯，右转转小弯，左转转大弯。右转转大弯是因为
         */

    }

}
