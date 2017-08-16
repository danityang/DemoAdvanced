package com.cdemo.viewstub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TODO 布局优化
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * ViewStub是一个轻量级的View，它一个看不见的，不占布局位置，占用资源非常小的控件。可以为ViewStub指定一个布局，在Inflate布局的时候，只有ViewStub会被初始化，然后当ViewStub被设置为可见的时候，或是调用了ViewStub.inflate()的时候，ViewStub所向的布局就会被Inflate和实例化，然后ViewStub的布局属性都会传给它所指向的布局。
         * 这样，就可以使用ViewStub来方便的在运行时，要还是不要显示某个布局
         * 特点：
         * 1. ViewStub只能Inflate一次，之后ViewStub对象会被置为空。按句话说，某个被ViewStub指定的布局被Inflate后，就不会够再通过ViewStub来控制它了。
         2. ViewStub只能用来Inflate一个布局文件，而不是某个具体的View，当然也可以把View写在某个布局文件中。
         基于以上的特点，那么可以考虑使用ViewStub的情况有：
         1. 在程序的运行期间，某个布局在Inflate后，就不会有变化，除非重新启动。
         因为ViewStub只能Inflate一次，之后会被置空，所以无法指望后面接着使用ViewStub来控制布局。所以当需要在运行时不止一次的显示和隐藏某个布局，那么ViewStub是做不到的。这时就只能使用View的可见性来控制了。
         2. 想要控制显示与隐藏的是一个布局文件，而非某个View。
         因为设置给ViewStub的只能是某个布局文件的Id，所以无法让它来控制某个View。
         所以，如果想要控制某个View(如Button或TextView)的显示与隐藏，或者想要在运行时不断的显示与隐藏某个布局或View，只能使用View的可见性来控制。
         下面来看一个实例
         在这个例子中，要显示二种不同的布局，一个是用TextView显示一段文字，另一个则是用ImageView显示一个图片。这二个是在onCreate()时决定是显示哪一个，这里就是应用ViewStub的最佳地点。
         */
        if ((((int) (Math.random() * 100)) & 0x01) == 0) {
            ViewStub stub = (ViewStub) findViewById(R.id.viewstub_text);
            stub.inflate();
            TextView text = (TextView) findViewById(R.id.text);
            text.setText("The tree of liberty must be refreshed from time to time" +
                    " with the blood of patroits and tyrants! Freedom is nothing but " +
                    "a chance to be better!");
        } else {
            ViewStub stub = (ViewStub) findViewById(R.id.viewstub_img);
            stub.inflate();
            ImageView image = (ImageView) findViewById(R.id.img);
            image.setImageResource(R.mipmap.ic_launcher_round);
        }
    }
}
