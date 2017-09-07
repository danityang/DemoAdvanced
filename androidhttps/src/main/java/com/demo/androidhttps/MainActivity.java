package com.demo.androidhttps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

public class MainActivity extends AppCompatActivity {

    TextView text;
    //    public static final String URL = "https://www.baidu.com/";
//    public static final String URL = "https://kyfw.12306.cn/otn/";
    public static final String URL = "https://openapi.4008990000.net/";
    public static final String TAG = "HttpsMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
    }


    public void netRequestClick(View v) {
        runOkHttp();
    }

    private void runOkHttp() {
        try {
            X509Certificate cert = getLocalX509Certificate();
            // TODO 生成一个包含服务器证书的keystore
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("cert", cert);

            // TODO 用包含服务器端证书的KeyStore生成一个TrustManager
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
            trustManagerFactory.init(keyStore);

            // TODO 生成一个使用我们自己的TrustManager的SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            OkHttpClient mOkHttpClient = new OkHttpClient().newBuilder().hostnameVerifier(hostnameVerifier)
                    .sslSocketFactory(sslContext.getSocketFactory()).build();
            Request request = new Request.Builder()
                    .url(URL)
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String str = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text.setText(str);
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 以HttpUrlConnection连接方式
     */
    public void runUrlConnection() {
        // TODO SSLContext
        SSLContext sslContext = null;
        // TODO TrustManager
        TrustManager[] trustManager = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    /**
                     * 验证服务端证书
                     * @param chain
                     * @param authType
                     * @throws CertificateException
                     */
                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        if (chain == null) {
                            throw new IllegalArgumentException("Server X509Certificates is null!");
                        } else if (chain.length < 0) {
                            throw new IllegalArgumentException("Server X509Certificates is empty!");
                        }
                        for (X509Certificate x : chain) {
                            x.checkValidity();
                            try {
                                x.verify(getLocalX509Certificate().getPublicKey());
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (InvalidKeyException e) {
                                e.printStackTrace();
                            } catch (NoSuchProviderException e) {
                                e.printStackTrace();
                            } catch (SignatureException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }

        };

        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager[0]}, null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成X509格式证书
     *
     * @return
     */
    private X509Certificate getLocalX509Certificate() {
        X509Certificate cert = null;
        try {
            // TODO 获取资源文件夹中面的预埋证书
            InputStream assetsInputStream = getAssets().open("srca.cer");
            // TODO or以字符串的形式获取预埋证书
            InputStream stringInputStream = new Buffer().writeUtf8(Constant.CERT).inputStream();
            // TODO 以X.509格式获取证书，https通信所用的数字证书格式为X.509
            CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
            cert = (X509Certificate) cerFactory.generateCertificate(assetsInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return cert;
    }


    /**
     * 服务器域名验证
     */
    HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            // TODO 强校验服务器端证书域名
//                    HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
//                    Boolean result = hv.verify("*.baidu.com", sslSession);
//                    Log.e(TAG, "域名校验: " + result);
//                    return result;
            return true;
        }
    };
}
