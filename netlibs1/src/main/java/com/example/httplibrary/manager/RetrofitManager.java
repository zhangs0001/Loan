package com.example.httplibrary.manager;


import com.example.httplibrary.config.GlobalConfig;
import com.example.httplibrary.constans.Constans;
import com.example.httplibrary.https.DefaultTrustManager;
import com.example.httplibrary.utils.LogUtils;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名：2007
 * 包名：  com.http.httplibrary.manager
 * 文件名：RetrofitManager
 * 创建者：liangxq
 * 创建时间：2020/11/4  9:52
 * 描述：TODO
 */
public class RetrofitManager {
    private OkHttpClient okHttpClient;
    private RetrofitManager() {

    }
    private static volatile RetrofitManager instance;
    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }

        }
        return instance;
    }

    private Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getBaseOkHttpClient())
                .build();
    }
    //没有https
    public OkHttpClient getBaseOkHttpClient() {
        return getHttpsOkHttpClient(null);
    }

    //https
    public OkHttpClient getHttpsOkHttpClient(String[] cers){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e("okhttp===" + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder .connectTimeout(GlobalConfig.getInstance().getTimeout(), Constans.TIME_UNIT_DEFAULT);
        builder .writeTimeout(GlobalConfig.getInstance().getTimeout(),Constans.TIME_UNIT_DEFAULT);
        builder .readTimeout(GlobalConfig.getInstance().getTimeout(),Constans.TIME_UNIT_DEFAULT);
        builder .retryOnConnectionFailure(true);
        builder .addInterceptor(httpLoggingInterceptor);
        if(GlobalConfig.getInstance().getInterceptors()!=null){
            List<Interceptor> interceptors = GlobalConfig.getInstance().getInterceptors();
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        //设置https证书
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            if (cers != null && cers.length>0) {
                setCerTrustManger(builder,cers);
            } else {
                X509TrustManager tm = DefaultTrustManager.getDefaultTrustManager();
                sc.init(null, new TrustManager[]{tm}, new SecureRandom());
                builder.sslSocketFactory(sc.getSocketFactory(), tm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    //有多个会变
    public <T> T createServer(Class<T>mClass ,String baseUrl){
        return getRetrofit(baseUrl).create(mClass);
    }
    //url只有一个不会变
    public <T> T createServer(Class<T>mClass ){
        return getRetrofit(GlobalConfig.getInstance().getBaseUrl()).create(mClass);
    }

    //设置https证书
    private static void setCerTrustManger(OkHttpClient.Builder builder, String[] certificates) {
        //CertificateFactory用来证书生成
        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            //Create a KeyStore containing our trusted CAs
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            for (int i = 0; i < certificates.length; i++) {
                //读取本地证书
                InputStream is = GlobalConfig.getInstance().getContext().getAssets().open(certificates[i]);
                keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(is));
                is.close();
            }
            //Create a TrustManager that trusts the CAs in our keyStore
            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            //Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            builder.sslSocketFactory(sslContext.getSocketFactory(), new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                    for (int i = 0; i < trustManagers.length; i++) {
                        TrustManager trustManager = trustManagers[i];
                        if (trustManager != null && trustManager instanceof X509TrustManager)
                            try {
                                ((X509TrustManager) trustManager).checkServerTrusted(chain, authType);
                            } catch (java.security.cert.CertificateException e) {
                                e.printStackTrace();
                            }
                    }
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            });
        } catch (Exception e) {
        }
    }
}
