package com.zte.mos.tools.st;

import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.jsonrpc4j.JsonRpcHttpAsyncClient;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.zte.mos.domain.DN;
import com.zte.mos.httpservice.MosService;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.inf.Maybe;
import com.zte.mos.util.tools.JsonUtil;
import org.eclipse.jetty.server.Server;
import org.junit.Test;

import javax.net.ssl.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.Future;

import static java.lang.System.nanoTime;

/**
 * Created by olinchy on 16-1-25.
 */
public class Test_https
{
    @Test
    public void test_async() throws Throwable
    {
        SSLContext sslcontext = getSslContext();
        JsonRpcHttpClient client = new JsonRpcHttpClient(new URL("https://localhost:54321"));
        client.setHostNameVerifier(new HostnameVerifier()
        {
            @Override
            public boolean verify(String s, SSLSession sslSession)
            {
                return true;
            }
        });
        client.setSslContext(sslcontext);

        JsonNode node = client.invoke(
                "login",
                new Object[]{"CLI", "ems", "ems", JsonUtil.toNode(new Maybe<Server>(null))},
                JsonNode.class);

        System.out.println(node.toString());

        JsonRpcHttpAsyncClient.setSSLContext(sslcontext);
        JsonRpcHttpAsyncClient async = new JsonRpcHttpAsyncClient(new URL("https://localhost:54321"));

        Future<JsonNode> responseNode = async.invoke(
                "ping", new Object[]{node.get("sessionId").asText()}, JsonNode.class);

        System.out.println(responseNode.get().toString());
    }

    @Test
    public void test() throws Throwable
    {

        SSLContext sslcontext = getSslContext();
        JsonRpcHttpClient client = new JsonRpcHttpClient(new URL("http://localhost:54321"));
        client.setHostNameVerifier(new HostnameVerifier()
        {
            @Override
            public boolean verify(String s, SSLSession sslSession)
            {
                return true;
            }
        });
        client.setSslContext(sslcontext);

        JsonNode node = client.invoke(
                "login",
                new Object[]{"CLI", "ems", "ems", JsonUtil.toNode(new Maybe<Server>(null))},
                JsonNode.class);

        System.out.println(node.toString());
    }

    private SSLContext getSslContext()
    {
        SSLContext sslcontext = null;
        try
        {
            sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
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

    private class TrustAnyTrustManager implements X509TrustManager
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

    @Test
    public void test_connection_time() throws Exception
    {
        // todo time consuming test
        long nano = nanoTime();

        MosServiceHttp service = new MosServiceHttp();

        // todo time consuming test log
        System.out.println("operation " + "login" + " cost " + (nanoTime() - nano) + " ns");

        for (int i = 0; i < 10; i++)
        {
            // todo time consuming test
            nano = nanoTime();

            service.get(new DN("/Ems/1"));

            // todo time consuming test log
            System.out.println("operation " + "get" + " cost " + (nanoTime() - nano) + " ns");
        }
    }
}
