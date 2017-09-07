package com.demo.messengerdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yangdi on 2017/8/23.
 */

public class MessengerServer extends Service {

    private Messenger mServerMessenger, mClientMessenger;
    private Message mReturnMessage;
    static String TAG = "MessengerServer";

    @Override
    public void onCreate() {
        super.onCreate();
        mServerMessenger = new Messenger(mHandler);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // TODO 接收客户端发来的消息
            if (msg.what == 0x11) {
                Log.i(TAG, "ServerHandleMessage: " + msg.getData().getString("cmsg"));
            }
            // TODO 获取客户端Messenger对象；客户端发送的Message的eplyto持有客户端Messenger对象
            mClientMessenger = msg.replyTo;
            // TODO 向客户端返回/返回数据
            mReturnMessage = Message.obtain(null, 0x12);
            Bundle bundle = new Bundle();
            // TODO 附带参数、要传的值
            bundle.putString("smsg", "来自ServerMessenger的消息+_—");
            mReturnMessage.setData(bundle);
            try {
                if (mClientMessenger != null) {
                    mClientMessenger.send(mReturnMessage);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mServerMessenger.getBinder();
    }
}
