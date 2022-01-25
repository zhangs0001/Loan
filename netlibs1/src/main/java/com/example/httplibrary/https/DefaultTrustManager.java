package com.example.httplibrary.https;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

/**
 * Created by hpw on 16/11/2.
 */

public class DefaultTrustManager {

    public static X509TrustManager getDefaultTrustManager(){
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };
    }
    public static HostnameVerifier getDefaultHostnameVerifier(){
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return false;
            }
        };
    }

}

