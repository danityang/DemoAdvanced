package com.demo.gimage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yangdi on 2017/8/17.
 */

public class ImgUtil {

    /**
     * 从网络上下载图片，获取写入流
     *
     * @param urlString
     * @return
     */
    public static byte[] getStream(String urlString) {
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = urlConnection.getInputStream();
            byte[] array = streamToBytes(in);
            return array;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private static byte[] streamToBytes(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (java.io.IOException e) {
        }
        return os.toByteArray();
    }
}
