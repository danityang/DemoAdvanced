package com.demo.messengerclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mResultText;
    private Messenger mClientMessenger, mServerMessenger;
    private Message mSendMessage;
    private boolean isConnected;
    private static String resultStr = "";
    public static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResultText = (TextView) findViewById(R.id.result_text);
        // 初始化客户端Messenger
        mClientMessenger = new Messenger(new HandlerClass());
        bindMessengerService();
    }

    /**
     * TODO 向服务端发送数据
     *
     * @param v
     */
    public void sendClick(View v) {
        // TODO 创建一个Message对象的写法，Message.obtain——>从持有 Message的MessagePool拿出，省去New一个message节约内存开支
        mSendMessage = Message.obtain(null, 0x11);
        Bundle bundle = new Bundle();
        // TODO 附带参数、要传的值
        bundle.putString("cmsg", "来自ClientMessenger的消息^_^");
        mSendMessage.setData(bundle);
        // TODO Message对象内部的replyTo赋值于ClientMessenger, 如果要实现客户端与服务端的双向通信，这句必写
        mSendMessage.replyTo = mClientMessenger;
        if (isConnected && mServerMessenger != null) {
            try {
                mServerMessenger.send(mSendMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handler
     */
    class HandlerClass extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // TODO 接收服务端发送过来的数据
            if (msg.what == 0x12) {
//                Log.i(TAG, "MainHandleMessage: " + msg.getData().getString("smsg"));
                resultStr += msg.getData().getString("smsg") + "\n";
                mResultText.setText(resultStr);
            }
        }
    }

    /**
     * ServiceConnection
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(MainActivity.this, "ServiceConnected", Toast.LENGTH_LONG).show();
            isConnected = true;
            // TODO 关键代码：通过IBinder(服务端Binder对象)获得服务端Messenger
            mServerMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "ServiceDisconnected", Toast.LENGTH_LONG).show();
            isConnected = false;
        }
    };

    /**
     * 服务端绑定 Messenger service
     */
    private void bindMessengerService() {
        Intent intent = new Intent();
        // TODO 参数1：服务端包名；参数2：服务端完整类名
        intent.setComponent(new ComponentName("com.demo.messengerdemo", "com.demo.messengerdemo.MessengerServer"));
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销/ 解绑Server
        unbindService(mServiceConnection);
    }
}
