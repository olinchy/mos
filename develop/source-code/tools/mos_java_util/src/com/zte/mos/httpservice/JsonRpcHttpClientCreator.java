package com.zte.mos.httpservice;

import com.googlecode.jsonrpc4j.JsonRpcHttpAsyncClient;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import javax.net.ssl.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by olinchy on 16-1-26.
 */
public class JsonRpcHttpClientCreator
{
    static SSLContext context = null;

    static
    {
        context = getSslContext();
        JsonRpcHttpAsyncClient.setSSLContext(context);
    }

    public static JsonRpcHttpClient create(URL server)
    {
        JsonRpcHttpClient client = new JsonRpcHttpClient(server);
        client.setHostNameVerifier(new HostnameVerifier()
        {
            @Override
            public boolean verify(String s, SSLSession sslSession)
            {
                return true;
            }
        });
        client.setSslContext(context);

        return client;
    }

    private static SSLContext getSslContext()
    {
        SSLContext sslcontext = null;
        try
        {
            sslcontext = SSLContext.getInstance("TLSv1");
            sslcontext.init(
                    null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (KeyManagementException e)
        {
            e.printStackTrace();
        }
        return sslcontext;
    }

    public static JsonRpcHttpAsyncClient createAsync(URL targetURL)
    {
        JsonRpcHttpAsyncClient async = new JsonRpcHttpAsyncClient(targetURL);
        return async;
    }

    private static class TrustAnyTrustManager implements X509TrustManager
    {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException
        {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException
        {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return new X509Certificate[0];
        }
    }
}
