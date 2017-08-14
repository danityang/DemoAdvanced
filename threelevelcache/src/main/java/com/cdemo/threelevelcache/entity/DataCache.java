package com.cdemo.threelevelcache.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.cdemo.threelevelcache.libcore.io.DiskLruCache;
import com.cdemo.threelevelcache.util.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yangdi on 2017/8/9.
 */

public class DataCache {

    Context mContext;

    // 内存缓存
    private LruCache<String, Bitmap> mMemoryCache;

    // 外部/硬盘缓存
    private DiskLruCache mDiskLruCache;

    private String TAG = "DataCache";

    public DataCache(Context context) {
        this.mContext = context;
        init();
    }

    /**
     * 初始化各个对象
     */
    private void init() {
        // 获取设备的内存容量
        int deviceMemory = (int) Runtime.getRuntime().maxMemory();
        // 设定缓存大小
        int cacheSize = deviceMemory / 8;
        // 实例化内存缓存对象
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
        try {
            // 设定外部缓存路径
            File cacheDir = Util.getDiskCacheDir(mContext, "thumb");
            if (!cacheDir.exists()) {
                cacheDir.mkdir();
            }
            // 创建/实例化DiskLruCache对象，通过open()方法, 接收四个参数:
            // 第一个参数指定的是数据的缓存地址，第二个参数指定当前应用程序的版本号，
            // 第三个参数指定同一个key可以对应多少个缓存文件，基本都是传1，第四个参数指定最多可以缓存多少字节的数据。
            mDiskLruCache = DiskLruCache.open(cacheDir, Util.getAppVersion(mContext), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存数据主要方法
     *
     * @param imageView
     * @param imgUrls
     * @return
     */
    public void getView(ImageView imageView, String imgUrls) {
        Bitmap diskBitmap;
        DiskLruCache.Snapshot snapShot;
        FileDescriptor fileDescriptor = null;
        FileInputStream fileInputStream = null;
        // 先从内存缓存中获取数据
        Bitmap memoryBitmap = getBitmapFromMemoryCache(imgUrls);
        if (memoryBitmap == null) {
            try {
                // 把对应图片的URL地址生成对应的MD5加密的key
                final String imgMd5Key = Util.hashKeyForDisk(imgUrls);
                Log.i(TAG, "getView: "+imgMd5Key);
                // 记忆缓存为空，再来查找外部缓存数据
                snapShot = mDiskLruCache.get(imgMd5Key);
                Log.i(TAG, "snapShot: "+snapShot);
                // 外部/硬盘缓存数据也为空，考虑开始从网络上下载
                if (snapShot == null) {
                    // 下载完成后再写入记忆缓存和外部/硬盘缓存
                    final DiskLruCache.Editor editor = mDiskLruCache.edit(imgMd5Key);
                    Log.i(TAG, "editor: "+editor);
                    // 外部缓存为空，从网上下载
                    if (editor != null) {
                        // 返回 OutputStream
                        final OutputStream outputStream = editor.newOutputStream(0);
                        Log.i(TAG, "outputStream: "+outputStream);
                        // 被订阅者
                        Observable.just(imgUrls)
                                .map(new Func1<String, Boolean>() {
                                    @Override
                                    public Boolean call(String s) {
                                        return downFromNet(s, outputStream);
                                    }
                                }).subscribeOn(Schedulers.io())
                                // 上面的 被订阅者 订阅 订阅者Action1
                                .subscribe(new Action1<Boolean>() {
                                    // 返回结果
                                    @Override
                                    public void call(Boolean aBoolean) {
                                        try {
                                            if (aBoolean) {
                                                // 下载完成后存入外部缓存
                                                // 通过editor.commit()方法提交写入，类似于SharedPrefence的Commit方法
                                                editor.commit();
                                            } else {
                                                editor.abort();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        Log.i(TAG, "call: "+throwable);
                                    }
                                });
                    }
                    // 缓存被写入后，再次查找key对应的缓存
                    // 获得Snapshot对象，通过Snapshot可获得一个InputStream对象
                    snapShot = mDiskLruCache.get(imgMd5Key);
                    diskBitmap = jugdeDiskLruCache(snapShot);
                    if (diskBitmap != null) {
                        // 再将刚存入的Bitmap对象添加到记忆缓存当中
                        addBitmapToMemoryCache(imgUrls, diskBitmap);
                        if (imageView != null) {
                            imageView.setImageBitmap(diskBitmap);
                        }
                    }
                } else {
                    diskBitmap = jugdeDiskLruCache(snapShot);
                    if (diskBitmap != null && imageView != null) {
                        imageView.setImageBitmap(diskBitmap);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (imageView != null) {
                imageView.setImageBitmap(memoryBitmap);
            }
        }
    }

    /**
     * 判定外部缓存对象SnapShot
     * @param snapshot
     * @return
     */
    private Bitmap jugdeDiskLruCache(DiskLruCache.Snapshot snapshot) {
        FileDescriptor fileDescriptor = null;
        FileInputStream fileInputStream;
        Bitmap fBitmap = null;
        try {
            if (snapshot != null) {
                fileInputStream = (FileInputStream) snapshot.getInputStream(0);
                fileDescriptor = fileInputStream.getFD();
            }
            if (fileDescriptor != null) {
                fBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            }
            if (fBitmap != null) {
                return fBitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 将一张图片存储到LruCache中。
     * @param key    LruCache的键，这里传入图片的URL地址。
     * @param bitmap LruCache的键，这里传入从网络上下载的Bitmap对象。
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    // 从设备缓存中获取数据
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }


    /**
     * 从网络上下载图片，获取写入流
     * @param urlString
     * @param outputStream
     * @return
     */
    private boolean downFromNet(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.i(TAG, "downFromNet: "+urlConnection);
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            Log.i(TAG, "in: "+in);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            Log.i(TAG, "out: "+out);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
