package com.alison.spring.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpClientPool {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_JSON_UTF8 = "application/json;charset=UTF-8";
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    private static final Logger logger = LoggerFactory.getLogger(HttpClientPool.class);
    private static final int REQUEST_TIMEOUT = 500;
    private static final int CONNECT_TIMEOUT = 500;
    private static final int SOCKET_TIMEOUT = 2000;
    private static final int HTTP_INIT_POOL_MAX_TOTAL = 500;
    private static final int HTTP_INIT_POOL_DEF_MAX_PER_ROUTE = 200;
    private static final int HTTP_INIT_POOL_CONN_REQ_TIME_OUT = 10000;
    private static final int HTTP_CLIENT_TRAN_CONN_TIME_OUT = 60000;
    private static final int HTTP_CLIENT_TRAN_CONN_REQ_TIME_OUT = 60000;
    private static final int HTTP_CLIENT_TRAN_SOCK_TIME_OUT = 60000;
    private static PoolingHttpClientConnectionManager poolConnManager;
    private static RequestConfig requestConfig;
    private static CloseableHttpClient httpClient;

    private static void config(HttpRequestBase httpRequestBase, Map<String, String> headers) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();
        httpRequestBase.addHeader(CONTENT_TYPE, CONTENT_TYPE_JSON_UTF8);
        if (headers != null) {
            headers.forEach((key, value) -> {
                httpRequestBase.addHeader(key, value);
            });
        }
        httpRequestBase.setConfig(requestConfig);
    }

    public static void initPool() {
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register(
                    HTTP, PlainConnectionSocketFactory.getSocketFactory()).register(
                    HTTPS, sslsf).build();
            poolConnManager = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            poolConnManager.setMaxTotal(HTTP_INIT_POOL_MAX_TOTAL);
            poolConnManager.setDefaultMaxPerRoute(HTTP_INIT_POOL_DEF_MAX_PER_ROUTE);
            int socketTimeout = SOCKET_TIMEOUT;
            int connectTimeout = CONNECT_TIMEOUT;
            int connectionRequestTimeout = HTTP_INIT_POOL_CONN_REQ_TIME_OUT;
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(
                    connectionRequestTimeout).setSocketTimeout(socketTimeout).setConnectTimeout(
                    connectTimeout).build();
            httpClient = createConnection();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException ex) {
            logger.error("init http client pool failed:", ex);
        }
    }

    public static CloseableHttpClient getConnection() {
        return httpClient;
    }

    public static CloseableHttpClient createConnection() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(poolConnManager)
                .setDefaultRequestConfig(requestConfig)
                .evictExpiredConnections()
                .evictIdleConnections(5, TimeUnit.SECONDS)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .build();
        return httpClient;
    }

    public static String post(String url, Map<String, Object> requestData) {
        return sendPost(url, requestData, null);
    }

    public static String post(String url, Map<String, Object> requestData, Map<String, String> headers) {
        return sendPost(url, requestData, headers);
    }

    public static String sendPost(String url, Map<String, Object> requestData, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(url);
        config(httpPost, headers);
        StringEntity stringEntity = new StringEntity(ObjectTransform.bean2Json(requestData), CHARSET_UTF8);
        stringEntity.setContentEncoding(CHARSET_UTF8);
        httpPost.setEntity(stringEntity);
        return getResponse(httpPost);
    }

    public static String sendPost(String url, String requestData, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(url);
        config(httpPost, headers);
        StringEntity stringEntity = new StringEntity(requestData, CHARSET_UTF8);
        stringEntity.setContentEncoding(CHARSET_UTF8);
        httpPost.setEntity(stringEntity);
        return getResponse(httpPost);
    }

    public static String get(String url, Map<String, String> headers) {
        return sendGet(url, headers);
    }

    public static String get(String url) {
        return sendGet(url, null);
    }

    public static String sendGet(String url, Map<String, String> headers) {
        HttpGet httpGet = new HttpGet(url);
        config(httpGet, headers);
        return getResponse(httpGet);
    }

    private static String getResponse(HttpRequestBase request) {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request,
                    HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, CHARSET_UTF8);
            EntityUtils.consume(entity);
            return result;
        } catch (IOException ex) {
            logger.error("get http response failed:", ex);
            return null;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException ex) {
                logger.error("get http response failed:", ex);
            }
        }
    }

    public static String transferPost(String url, Map<String, Object> requestData) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(HTTP_CLIENT_TRAN_CONN_REQ_TIME_OUT)
                .setConnectTimeout(HTTP_CLIENT_TRAN_CONN_TIME_OUT)
                .setSocketTimeout(HTTP_CLIENT_TRAN_SOCK_TIME_OUT).build();
        httpPost.addHeader(CONTENT_TYPE, CONTENT_TYPE_JSON_UTF8);
        httpPost.setConfig(requestConfig);
        StringEntity stringEntity = new StringEntity(ObjectTransform.bean2Json(requestData), CHARSET_UTF8);
        stringEntity.setContentEncoding(CHARSET_UTF8);
        httpPost.setEntity(stringEntity);
        return getResponse(httpPost);
    }
}
